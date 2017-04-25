package org.jooq.example.migrator;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;

public abstract class JooqMigration implements JdbcMigration {

    @Override
    public final void migrate(Connection connection) throws Exception {
        DSLContext create = DSL.using(connection, SQLDialect.H2);
        migrate(connection, create);
    }

    public abstract void migrate(Connection connection, DSLContext create) throws Exception;
}
