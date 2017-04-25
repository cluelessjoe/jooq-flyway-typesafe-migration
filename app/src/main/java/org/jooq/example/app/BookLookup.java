package org.jooq.example.app;

import org.jooq.DSLContext;
import org.jooq.example.common.DbInfo;
import org.jooq.impl.DSL;

import java.util.List;

import static org.jooq.example.app.model.tables.Book.BOOK;

public class BookLookup {

    private final DSLContext create;

    public BookLookup(DbInfo dbInfo) {
        create = DSL.using(dbInfo.getDbUrl(), dbInfo.getUser(), dbInfo.getPassword());
    }

    public List<String> getBooksNames(int from, int nbOfResults) {
        return create.select().from(BOOK).offset(from).limit(nbOfResults).fetch(BOOK.TITLE);
    }
}
