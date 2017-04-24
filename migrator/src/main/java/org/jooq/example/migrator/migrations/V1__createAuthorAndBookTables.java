package org.jooq.example.migrator.migrations;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.sql.Connection;

import static org.jooq.impl.DSL.constraint;

public class V1__createAuthorAndBookTables implements JdbcMigration {

    @Override
    public void migrate(Connection connection) throws Exception {
        DSLContext create = DSL.using(connection, SQLDialect.H2);

        create.createSequence("S_AUTHOR_ID").execute();

        String authorTableName = "AUTHOR";
        create.createTable(authorTableName)
                .column("ID", SQLDataType.INTEGER.nullable(false))
                .column("FIRST_NAME", SQLDataType.VARCHAR.length(50))
                .column("LAST_NAME", SQLDataType.VARCHAR.length(50).nullable(false))
                .column("DATE_OF_BIRTH", SQLDataType.DATE)
                .column("YEAR_OF_BIRTH", SQLDataType.INTEGER)
                .column("ADDRESS", SQLDataType.VARCHAR.length(50))
                .execute();

        create.alterTable(authorTableName).add(constraint("PK_T_AUTHOR").primaryKey("ID")).execute();

        String bookTableName = "BOOK";
        create.createTable(bookTableName)
                .column("ID", SQLDataType.INTEGER.nullable(false))
                .column("AUTHOR_ID", SQLDataType.INTEGER.nullable(false))
                .column("TITLE", SQLDataType.VARCHAR.length(400).nullable(false))
                .execute();

        create.alterTable(bookTableName).add(constraint("PK_T_BOOK").primaryKey("ID")).execute();
        create.alterTable(bookTableName).add(
                constraint("FK_T_BOOK_AUTHOR_ID").foreignKey("AUTHOR_ID").references(authorTableName, "ID"))
                .execute();

    }
}
