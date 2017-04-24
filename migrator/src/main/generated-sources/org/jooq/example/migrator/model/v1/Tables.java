/*
 * This file is generated by jOOQ.
*/
package org.jooq.example.migrator.model.v1;


import javax.annotation.Generated;

import org.jooq.example.migrator.model.v1.tables.Author;
import org.jooq.example.migrator.model.v1.tables.Book;
import org.jooq.example.migrator.model.v1.tables.SchemaVersion;


/**
 * Convenience access to all tables in FLYWAY_TEST
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.2",
        "schema version:FLYWAY_TEST_1"
    },
    date = "2017-04-24T21:32:45.313Z",
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>FLYWAY_TEST.AUTHOR</code>.
     */
    public static final Author AUTHOR = org.jooq.example.migrator.model.v1.tables.Author.AUTHOR;

    /**
     * The table <code>FLYWAY_TEST.BOOK</code>.
     */
    public static final Book BOOK = org.jooq.example.migrator.model.v1.tables.Book.BOOK;

    /**
     * The table <code>FLYWAY_TEST.schema_version</code>.
     */
    public static final SchemaVersion SCHEMA_VERSION = org.jooq.example.migrator.model.v1.tables.SchemaVersion.SCHEMA_VERSION;
}