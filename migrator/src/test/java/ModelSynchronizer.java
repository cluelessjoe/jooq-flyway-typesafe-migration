import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.callback.BaseFlywayCallback;
import org.jooq.example.common.DbInfo;
import org.jooq.example.migrator.JooqMigration;
import org.jooq.example.migrator.Migrator;
import org.jooq.util.GenerationTool;
import org.jooq.util.jaxb.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.Optional;

class ModelSynchronizer extends BaseFlywayCallback {

    public static final String VERSION_PROPERTY_KEY = "VERSION";

    private final DbInfo dbInfo;
    private final File projectRoot;
    private final String migratorModuleName;
    private final String appModuleName;
    private final String migratorGeneratedSourcesDir;
    private final String appGeneratedSourcesDir;
    private final String migratorModelPackage;
    private final String appModelPackage;
    private final String generatedSourceDir;
    private final Path appGeneratedSourcesPackageDir;

    public ModelSynchronizer(DbInfo dbInfo) {
        this.dbInfo = dbInfo;
        projectRoot = determineProjectRoot();
        generatedSourceDir = "src/main/generated-sources/";
        migratorModuleName = "migrator";
        appModuleName = "app";
        migratorGeneratedSourcesDir = computeGeneratedSourceDir(migratorModuleName);
        migratorModelPackage = "org.jooq.example.migrator.model";
        appGeneratedSourcesDir = computeGeneratedSourceDir(appModuleName);
        appModelPackage = "org.jooq.example.app.model";
        appGeneratedSourcesPackageDir = Paths.get(projectRoot.getAbsolutePath(), appModuleName, generatedSourceDir + toDir(appModelPackage));
    }

    @Override
    public void afterEachMigrate(Connection connection, MigrationInfo info) {
        super.afterEachMigrate(connection, info);
        String currentVersion = info.getVersion().getVersion();
        if (JooqMigration.didExecute) {
            String migratorGeneratedModelPackage = migratorModelPackage + ".v" + currentVersion;
            Path migratorGeneratedSourcesPackage = computeMigratorGeneratedSourcePackageDir(migratorGeneratedModelPackage);
            if (JooqMigration.ddlInstructionExecuted) {
                System.out.println("Generating migration model for version " + currentVersion + " in " + migratorGeneratedSourcesDir);
                deleteRecursivelyIfExisting(migratorGeneratedSourcesPackage);
                generate(createConfiguration(migratorGeneratedSourcesDir, migratorGeneratedModelPackage, Optional.of(currentVersion)));
            } else {
                System.out.println("No migration model for version " + currentVersion + " in " + migratorGeneratedSourcesDir);
                deleteRecursivelyIfExisting(migratorGeneratedSourcesPackage);
            }
        }
    }

    @Override
    public void afterMigrate(Connection connection) {
        super.afterMigrate(connection);
        if (JooqMigration.didExecute) {
            System.out.println("Generating " + appModuleName + " module model in " + appGeneratedSourcesDir);
            deleteRecursivelyIfExisting(appGeneratedSourcesPackageDir);
            generate(createConfiguration(appGeneratedSourcesDir, appModelPackage));
        }
    }

    private Path computeMigratorGeneratedSourcePackageDir(String migratorGeneratedModelPackage) {
        return Paths.get(
                migratorGeneratedSourcesDir,
                toDir(migratorGeneratedModelPackage));
    }

    private Configuration createConfiguration(String generatedSourcesDir, String generatedPackageName, Optional<String> currentVersion) {
        Generator generator = new Generator();
        currentVersion.ifPresent(v -> generator.withStrategy(new Strategy().withName("PrefixingGeneratorStrategy")));
        Database database = new Database()
                .withName("org.jooq.util.h2.H2Database")
                .withIncludes(".*")
                .withExcludes("")
                .withInputSchema(Migrator.FLYWAY_TEST)
                .withSchemaVersionProvider("SELECT :schema_name || '_' || MAX(\"version\") FROM \"" + Migrator.FLYWAY_TEST + "\".\"schema_version\"");
        currentVersion.ifPresent(version -> database.withProperties(new Property().withKey(VERSION_PROPERTY_KEY).withValue("v" + version)));

        Configuration configuration = new Configuration()
                .withJdbc(new Jdbc()
                        .withDriver("org.h2.Driver")
                        .withUrl(dbInfo.getDbUrl())
                        .withUser(dbInfo.getUser())
                        .withPassword(dbInfo.getPassword()))
                .withGenerator(generator
                        .withDatabase(database)
                        .withTarget(new Target()
                                .withPackageName(generatedPackageName)
                                .withDirectory(generatedSourcesDir)));
        return configuration;
    }

    private Configuration createConfiguration(String generatedSourcesDir, String generatedPackageName) {
        return createConfiguration(generatedSourcesDir, generatedPackageName, Optional.empty());
    }

    private String computeGeneratedSourceDir(String module) {
        return new File(projectRoot, module + "/" + generatedSourceDir).getAbsolutePath();
    }

    private void generate(Configuration configuration) {
        try {
            GenerationTool.generate(configuration);
        } catch (Exception e) {
            throw new RuntimeException("Model generation in " + configuration.getGenerator().getTarget().getDirectory() + " failed", e);
        }
    }

    private File determineProjectRoot() {
        File generatedClassDir = fileFromUrl(Migrator.class.getClassLoader().getResource(""));
        String generatedClassDirPath = generatedClassDir.getAbsolutePath();
        if (!generatedClassDirPath.endsWith("classes")) {
            throw new IllegalStateException("Expected a generated classes directory, got " + generatedClassDirPath);
        }
        return generatedClassDir.getParentFile().getParentFile().getParentFile();
    }

    private File fileFromUrl(URL url) {
        try {
            return new File(url.toURI());
        } catch (URISyntaxException ignored) {
            return new File(url.getPath());
        }
    }

    private void deleteRecursivelyIfExisting(Path p) {
        if (Files.exists(p)) {
            deleteRecursively(p);
        }
    }

    private void deleteRecursively(Path p) {
        try {
            if (Files.isDirectory(p)) {
                Files.list(p).forEach(this::deleteRecursively);
            }
            Files.delete(p);
        } catch (IOException e) {
            throw new RuntimeException("When trying to delete " + p, e);
        }
    }

    private String toDir(String packageName) {
        return packageName.replace(".", "/");
    }
}
