package org.jooq.example.migrator;

import org.flywaydb.core.Flyway;

public class Migrator {

    public static final String FLYWAY_TEST = "FLYWAY_TEST";

    public int migrateAndReturnCurrentVersion(String dbUrl, String user, String password) {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:h2:~/h2testDb", "sa", null);
        flyway.setSchemas(FLYWAY_TEST);
        flyway.setLocations(Migrator.class.getPackage().getName() + "/migrations");
        flyway.migrate();
        return Integer.parseInt(flyway.info().current().getVersion().getVersion());
    }
}
