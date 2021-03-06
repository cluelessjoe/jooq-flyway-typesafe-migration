/*
 * This file is generated by jOOQ.
*/
package org.jooq.example.migrator.model.v3.tables;


import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.example.migrator.model.v3.Keys;
import org.jooq.example.migrator.model.v3.tables.records.v3_AuthorRecord;
import org.jooq.example.migrator.model.v3.v3_FlywayTest;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
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
public class v3_Author extends TableImpl<v3_AuthorRecord> {

    private static final long serialVersionUID = -318680454;

    /**
     * The reference instance of <code>FLYWAY_TEST.AUTHOR</code>
     */
    public static final v3_Author v3_AUTHOR = new v3_Author();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<v3_AuthorRecord> getRecordType() {
        return v3_AuthorRecord.class;
    }

    /**
     * The column <code>FLYWAY_TEST.AUTHOR.ID</code>.
     */
    public final TableField<v3_AuthorRecord, Integer> v3_ID = createField("ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>FLYWAY_TEST.AUTHOR.FIRST_NAME</code>.
     */
    public final TableField<v3_AuthorRecord, String> v3_FIRST_NAME = createField("FIRST_NAME", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

    /**
     * The column <code>FLYWAY_TEST.AUTHOR.LAST_NAME</code>.
     */
    public final TableField<v3_AuthorRecord, String> v3_LAST_NAME = createField("LAST_NAME", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

    /**
     * The column <code>FLYWAY_TEST.AUTHOR.DATE_OF_BIRTH</code>.
     */
    public final TableField<v3_AuthorRecord, Date> v3_DATE_OF_BIRTH = createField("DATE_OF_BIRTH", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * The column <code>FLYWAY_TEST.AUTHOR.YEAR_OF_BIRTH</code>.
     */
    public final TableField<v3_AuthorRecord, Integer> v3_YEAR_OF_BIRTH = createField("YEAR_OF_BIRTH", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>FLYWAY_TEST.AUTHOR.ADDRESS</code>.
     */
    public final TableField<v3_AuthorRecord, String> v3_ADDRESS = createField("ADDRESS", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "");

    /**
     * The column <code>FLYWAY_TEST.AUTHOR.GENDER</code>.
     */
    public final TableField<v3_AuthorRecord, Integer> v3_GENDER = createField("GENDER", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>FLYWAY_TEST.AUTHOR</code> table reference
     */
    public v3_Author() {
        this("AUTHOR", null);
    }

    /**
     * Create an aliased <code>FLYWAY_TEST.AUTHOR</code> table reference
     */
    public v3_Author(String alias) {
        this(alias, v3_AUTHOR);
    }

    private v3_Author(String alias, Table<v3_AuthorRecord> aliased) {
        this(alias, aliased, null);
    }

    private v3_Author(String alias, Table<v3_AuthorRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return v3_FlywayTest.v3_FLYWAY_TEST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<v3_AuthorRecord> getPrimaryKey() {
        return Keys.v3_PK_T_AUTHOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<v3_AuthorRecord>> getKeys() {
        return Arrays.<UniqueKey<v3_AuthorRecord>>asList(Keys.v3_PK_T_AUTHOR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public v3_Author as(String alias) {
        return new v3_Author(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public v3_Author rename(String name) {
        return new v3_Author(name, null);
    }
}
