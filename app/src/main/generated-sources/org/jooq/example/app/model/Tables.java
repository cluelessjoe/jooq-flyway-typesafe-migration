/*
 * This file is generated by jOOQ.
*/
package org.jooq.example.app.model;


import javax.annotation.Generated;

import org.jooq.example.app.model.tables.Author;
import org.jooq.example.app.model.tables.Book;
import org.jooq.example.app.model.tables.SchemaVersion;


/**
 * Convenience access to all tables in FLYWAY_TEST
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.2",
        "schema version:FLYWAY_TEST_3"
    },
    date = "2017-05-16T21:33:40.793Z",
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>FLYWAY_TEST.AUTHOR</code>.
     */
    public static final Author AUTHOR = org.jooq.example.app.model.tables.Author.AUTHOR;

    /**
     * The table <code>FLYWAY_TEST.BOOK</code>.
     */
    public static final Book BOOK = org.jooq.example.app.model.tables.Book.BOOK;

    /**
     * The table <code>FLYWAY_TEST.schema_version</code>.
     */
    public static final SchemaVersion SCHEMA_VERSION = org.jooq.example.app.model.tables.SchemaVersion.SCHEMA_VERSION;
}
