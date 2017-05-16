package org.jooq.example.migrator;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.callback.FlywayCallback;
import org.jooq.example.common.DbInfo;

public class Migrator {

    public static final String FLYWAY_TEST = "FLYWAY_TEST";
    public static final FlywayCallback[] DEFAULT_CALLBACKS = new FlywayCallback[0];
    private final DbInfo dbInfo;
    private final FlywayCallback[] callbacks;

    public Migrator(DbInfo dbInfo) {
        this(dbInfo, DEFAULT_CALLBACKS);
    }

    public Migrator(DbInfo dbInfo, FlywayCallback... callbacks) {
        this.dbInfo = dbInfo;
        this.callbacks = callbacks != null ? callbacks : DEFAULT_CALLBACKS;
    }

    public void migrate() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dbInfo.getDbUrl(), dbInfo.getUser(), dbInfo.getPassword());
        flyway.setSchemas(FLYWAY_TEST);
        flyway.setLocations(Migrator.class.getPackage().getName() + "/migrations");
        flyway.setCallbacks(callbacks);
        flyway.migrate();
    }
}
