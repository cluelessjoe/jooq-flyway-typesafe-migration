package org.jooq.example.migrator.migrations;

import org.jooq.DSLContext;
import org.jooq.example.migrator.JooqMigration;
import org.jooq.example.migrator.model.v1.Sequences;

import java.sql.Connection;

import static org.jooq.example.migrator.model.v1.Tables.v1_BOOK;
import static org.jooq.example.migrator.model.v1.tables.v1_Author.v1_AUTHOR;

public class V2__addRecords extends JooqMigration {

    @Override
    public void migrate(Connection connection, DSLContext create) throws Exception {
        create.insertInto(v1_AUTHOR).values(Sequences.v1_S_AUTHOR_ID.nextval(), "George", "Orwell", "1903-06-25", 1903, null).execute();
        create.insertInto(v1_AUTHOR).values(Sequences.v1_S_AUTHOR_ID.nextval(), "Paulo", "Coelho", "1947-08-24", 1947, null).execute();
        connection.setAutoCommit(false);
        create.insertInto(v1_BOOK).values(1, 1, "1984").execute();
        create.insertInto(v1_BOOK).values(2, 1, "Animal Farm").execute();
        create.insertInto(v1_BOOK).values(3, 2, "O Alquimista").execute();
        create.insertInto(v1_BOOK).values(4, 2, "Brida").execute();
        connection.commit();
    }
}
