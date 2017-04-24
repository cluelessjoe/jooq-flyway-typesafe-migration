package org.jooq.example.migrator.migrations;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.sql.Connection;

import static org.jooq.example.migrator.model.v1.tables.Author.AUTHOR;

public class V3__increase implements JdbcMigration {

    @Override
    public void migrate(Connection connection) throws Exception {
        DSLContext create = DSL.using(connection, SQLDialect.H2);

        create.alterTable(AUTHOR)
                .alterColumn(AUTHOR.FIRST_NAME)
                .set(SQLDataType.VARCHAR.length(100))
                .execute();

        create.alterTable(AUTHOR)
                .addColumn("GENDER", SQLDataType.INTEGER)
                .execute();
    }
}
