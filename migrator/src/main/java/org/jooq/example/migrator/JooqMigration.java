package org.jooq.example.migrator;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.JDBCUtils;

import java.sql.Connection;

public abstract class JooqMigration implements JdbcMigration {

    @Override
    public final void migrate(Connection connection) throws Exception {
        DSLContext create = DSL.using(connection, extraDialectOrThrow(connection));
        migrate(connection, create);
    }

    private SQLDialect extraDialectOrThrow(Connection connection) {
        SQLDialect dialect = JDBCUtils.dialect(connection);
        if(SQLDialect.DEFAULT.equals(dialect))
            throw new IllegalStateException("Dialect couldn't be deducted from connection " + connection);
        return dialect;
    }

    public abstract void migrate(Connection connection, DSLContext create) throws Exception;
}
