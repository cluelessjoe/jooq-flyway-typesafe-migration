/*
 * This file is generated by jOOQ.
*/
package org.jooq.example.migrator.model.v3;


import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.UniqueKey;
import org.jooq.example.migrator.model.v3.tables.records.v3_AuthorRecord;
import org.jooq.example.migrator.model.v3.tables.records.v3_BookRecord;
import org.jooq.example.migrator.model.v3.tables.records.v3_SchemaVersionRecord;
import org.jooq.example.migrator.model.v3.tables.v3_Author;
import org.jooq.example.migrator.model.v3.tables.v3_Book;
import org.jooq.example.migrator.model.v3.tables.v3_SchemaVersion;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships between tables of the <code>FLYWAY_TEST</code> 
 * schema
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.2",
        "schema version:FLYWAY_TEST_2"
    },
    date = "2017-05-16T21:33:40.682Z",
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<v3_AuthorRecord> v3_PK_T_AUTHOR = UniqueKeys0.v3_PK_T_AUTHOR;
    public static final UniqueKey<v3_BookRecord> v3_PK_T_BOOK = UniqueKeys0.v3_PK_T_BOOK;
    public static final UniqueKey<v3_SchemaVersionRecord> v3_SCHEMA_VERSION_PK = UniqueKeys0.v3_SCHEMA_VERSION_PK;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<v3_BookRecord, v3_AuthorRecord> v3_FK_T_BOOK_AUTHOR_ID = ForeignKeys0.v3_FK_T_BOOK_AUTHOR_ID;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<v3_AuthorRecord> v3_PK_T_AUTHOR = createUniqueKey(v3_Author.v3_AUTHOR, "PK_T_AUTHOR", v3_Author.v3_AUTHOR.v3_ID);
        public static final UniqueKey<v3_BookRecord> v3_PK_T_BOOK = createUniqueKey(v3_Book.v3_BOOK, "PK_T_BOOK", v3_Book.v3_BOOK.v3_ID);
        public static final UniqueKey<v3_SchemaVersionRecord> v3_SCHEMA_VERSION_PK = createUniqueKey(v3_SchemaVersion.v3_SCHEMA_VERSION, "schema_version_pk", v3_SchemaVersion.v3_SCHEMA_VERSION.v3_INSTALLED_RANK);
    }

    private static class ForeignKeys0 extends AbstractKeys {
        public static final ForeignKey<v3_BookRecord, v3_AuthorRecord> v3_FK_T_BOOK_AUTHOR_ID = createForeignKey(org.jooq.example.migrator.model.v3.Keys.v3_PK_T_AUTHOR, v3_Book.v3_BOOK, "FK_T_BOOK_AUTHOR_ID", v3_Book.v3_BOOK.v3_AUTHOR_ID);
    }
}
