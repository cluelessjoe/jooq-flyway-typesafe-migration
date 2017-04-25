package org.jooq.example.migrator.migrations;

import org.jooq.DSLContext;
import org.jooq.example.migrator.JooqMigration;
import org.jooq.impl.SQLDataType;

import java.sql.Connection;

import static org.jooq.example.migrator.model.v1.tables.Author.AUTHOR;

public class V3__increase extends JooqMigration {

    @Override
    public void migrate(Connection connection, DSLContext create) {
        create.alterTable(AUTHOR)
                .alterColumn(AUTHOR.FIRST_NAME)
                .set(SQLDataType.VARCHAR.length(100))
                .execute();

        create.alterTable(AUTHOR)
                .addColumn("GENDER", SQLDataType.INTEGER)
                .execute();
    }
}
