import org.jooq.example.migrator.Migrator;
import org.jooq.util.GenerationTool;
import org.jooq.util.jaxb.*;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LatestModelGenerator {

    public static void main(String[] args) throws Exception {
        new LatestModelGenerator("jdbc:h2:~/h2testDb", "sa", "").generateNextModel();
    }

    private final String dbUrl;
    private final String user;
    private final String password;
    private final File projectRoot;
    private final String migratorModuleName;
    private final String appModuleName;
    private final String migratorGeneratedSourcesDir;
    private final String appGeneratedSourcesDir;
    private final String migratorModelPackage;
    private final String appModelPackage;
    private final String generatedSourceDir;
    private final Path appGeneratedSourcesPackageDir;

    public LatestModelGenerator(String dbUrl, String user, String password) {
        this.dbUrl = dbUrl;
        this.user = user;
        this.password = password;
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

    private void generateNextModel() {
        int currentVersion = new Migrator().migrateAndReturnCurrentVersion(dbUrl, user, password);

        String migratorGeneratedModelPackage = migratorModelPackage + ".v" + currentVersion;
        Path migratorGeneratedSourcesPackage = computeMigratorGeneratedSourcePackageDir(migratorGeneratedModelPackage);

        System.out.println("Generating migration model for version " + currentVersion + " in " + migratorGeneratedSourcesDir);
        deleteRecursivelyIfExisting(migratorGeneratedSourcesPackage);
        generate(createConfiguration(migratorGeneratedSourcesDir, migratorGeneratedModelPackage));

        System.out.println("Generating " + appModuleName + " module model for version " + currentVersion + " in " + appGeneratedSourcesDir);
        deleteRecursivelyIfExisting(appGeneratedSourcesPackageDir);
        generate(createConfiguration(appGeneratedSourcesDir, appModelPackage));
    }

    private Path computeMigratorGeneratedSourcePackageDir(String migratorGeneratedModelPackage) {
        return Paths.get(
                migratorGeneratedSourcesDir,
                toDir(migratorGeneratedModelPackage));
    }

    private Configuration createConfiguration(String generatedSourcesDir, String generatedPackageName) {
        return new Configuration()
                .withJdbc(new Jdbc()
                        .withDriver("org.h2.Driver")
                        .withUrl(dbUrl)
                        .withUser(user)
                        .withPassword(password))
                .withGenerator(new Generator()
                        .withDatabase(new Database()
                                .withName("org.jooq.util.h2.H2Database")
                                .withIncludes(".*")
                                .withExcludes("")
                                .withInputSchema(Migrator.FLYWAY_TEST)
                                .withSchemaVersionProvider("SELECT :schema_name || '_' || MAX(\"version\") FROM \"" + Migrator.FLYWAY_TEST + "\".\"schema_version\""))
                        .withTarget(new Target()
                                .withPackageName(generatedPackageName)
                                .withDirectory(generatedSourcesDir)));
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
        File f = p.toFile();
        if (f.exists()) {
            System.out.println("Deleting recursively content in " + f.getAbsolutePath());
            deleteRecursively(f);
        }
    }

    private void deleteRecursively(File f) {
        if (f.isDirectory()) {
            for (File c : f.listFiles())
                deleteRecursively(c);
        }
        if (!f.delete())
            throw new RuntimeException("Failed to delete file: " + f);
    }

    private String toDir(String packageName) {
        return packageName.replace(".", "/");
    }

}