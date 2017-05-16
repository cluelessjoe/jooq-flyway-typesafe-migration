package org.jooq.example.migrator;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.JDBCUtils;

import java.sql.Connection;

public class JooqMigrationUtils {

    public static DSLContext createDslContext(Connection connection) {
        return DSL.using(connection, extractDialectOrThrow(connection));
    }

    private static SQLDialect extractDialectOrThrow(Connection connection) {
        SQLDialect dialect = JDBCUtils.dialect(connection);
        if (SQLDialect.DEFAULT.equals(dialect))
            throw new IllegalStateException("Dialect couldn't be deducted from connection " + connection);
        return dialect;
    }

}
