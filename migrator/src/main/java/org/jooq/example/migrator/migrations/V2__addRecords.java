package org.jooq.example.migrator.migrations;

import org.jooq.DSLContext;
import org.jooq.example.migrator.JooqMigration;
import org.jooq.example.migrator.model.v1.Sequences;

import java.sql.Connection;

import static org.jooq.example.migrator.model.v1.tables.Author.AUTHOR;
import static org.jooq.example.migrator.model.v1.tables.Book.BOOK;

public class V2__addRecords extends JooqMigration {

    @Override
    public void migrate(Connection connection, DSLContext create) throws Exception{
        create.insertInto(AUTHOR).values(Sequences.S_AUTHOR_ID.nextval(), "George", "Orwell", "1903-06-25", 1903, null).execute();
        create.insertInto(AUTHOR).values(Sequences.S_AUTHOR_ID.nextval(), "Paulo", "Coelho", "1947-08-24", 1947, null).execute();
        connection.setAutoCommit(false);
        create.insertInto(BOOK).values(1, 1, "1984").execute();
        create.insertInto(BOOK).values(2, 1, "Animal Farm").execute();
        create.insertInto(BOOK).values(3, 2, "O Alquimista").execute();
        create.insertInto(BOOK).values(4, 2, "Brida").execute();
        connection.commit();
    }
}
