import org.jooq.example.migrator.Migrator;
import org.jooq.util.GenerationTool;
import org.jooq.util.jaxb.*;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NextModelGenerator {

    public static void main(String[] args) throws Exception {
        new NextModelGenerator().generateNextModel();

    }

    private void generateNextModel() {
        String dbUrl = "jdbc:h2:~/h2testDb";
        String user = "sa";
        String password = "";
        File projectRoot = determineProjectRoot();

        int currentVersion = new Migrator().migrateAndReturnCurrentVersion(dbUrl, user, password);

        String migratorGeneratedSourcesDir = computeGeneratedSourceFolder(projectRoot, "migrator");
        Path migratorGeneratedSourcesPackage = computeMigratorGeneratedSourcePackage(migratorGeneratedSourcesDir, currentVersion);
        deleteRecursivelyIfExisting(migratorGeneratedSourcesPackage.toFile());

        System.out.println("Generating migration model for version " + currentVersion + " in " + migratorGeneratedSourcesDir);
        generate(createConfiguration(dbUrl, user, password, migratorGeneratedSourcesDir, "org.jooq.example.migrator.model.v" + currentVersion));

        String businessModule = "app";
        String appGeneratedSourcesDir = computeGeneratedSourceFolder(projectRoot, businessModule);
        Path appGeneratedSourcesPackage = Paths.get(projectRoot.getAbsolutePath(), businessModule, "src/main/generated-sources/org/jooq/example/app/model");
        System.out.println("Deleting and generating " + businessModule + " module model for version " + currentVersion + " in " + appGeneratedSourcesDir);
        deleteRecursivelyIfExisting(appGeneratedSourcesPackage.toFile());
        generate(createConfiguration(dbUrl, user, password, appGeneratedSourcesDir, "org.jooq.example.app.model"));
    }

    private Path computeMigratorGeneratedSourcePackage(String migratorGeneratedSourcesDir, int currentVersion) {
        return Paths.get(
                migratorGeneratedSourcesDir,
                Migrator.class.getPackage().getName().replace(".", "/"),
                "/model",
                "/v" + currentVersion);
    }

    private Configuration createConfiguration(String dbUrl, String user, String password, String generatedSourcesFolder, String generatedPackageName) {
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
                                .withDirectory(generatedSourcesFolder)));
    }

    private String computeGeneratedSourceFolder(File projectRoot, String module) {
        return new File(projectRoot, module + "/src/main/generated-sources/").getAbsolutePath();
    }

    private void generate(Configuration configuration) {
        try {
            GenerationTool.generate(configuration);
        } catch (Exception e) {
            throw new RuntimeException("Model generation in " + configuration.getGenerator().getTarget().getDirectory() + " failed", e);
        }
    }

    private File determineProjectRoot() {
        File generatedClassFolder = fileFromUrl(Migrator.class.getClassLoader().getResource(""));
        String generatedClassFolderPath = generatedClassFolder.getAbsolutePath();
        if (!generatedClassFolderPath.endsWith("classes")) {
            throw new IllegalStateException("Expected the generated folder, got " + generatedClassFolderPath);
        }
        return generatedClassFolder.getParentFile().getParentFile().getParentFile();
    }

    private File fileFromUrl(URL url) {
        try {
            return new File(url.toURI());
        } catch (URISyntaxException ignored) {
            return new File(url.getPath());
        }
    }

    private void deleteRecursivelyIfExisting(File f) {
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
}