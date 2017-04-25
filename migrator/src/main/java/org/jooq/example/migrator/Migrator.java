package org.jooq.example.migrator;

import org.flywaydb.core.Flyway;
import org.jooq.example.common.DbInfo;

public class Migrator {

    public static final String FLYWAY_TEST = "FLYWAY_TEST";
    private final DbInfo dbInfo;

    public Migrator(DbInfo dbInfo) {
        this.dbInfo = dbInfo;
    }

    public String migrateAndReturnCurrentVersion() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dbInfo.getDbUrl(), dbInfo.getUser(), dbInfo.getPassword());
        flyway.setSchemas(FLYWAY_TEST);
        flyway.setLocations(Migrator.class.getPackage().getName() + "/migrations");
        flyway.migrate();
        return flyway.info().current().getVersion().getVersion();
    }
}
