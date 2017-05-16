package org.jooq.example.runner;

import org.jooq.example.app.BookLookup;
import org.jooq.example.common.DbInfo;
import org.jooq.example.migrator.Migrator;

public class Runner {

    private final DbInfo dbInfo;

    public Runner(DbInfo dbInfo) {
        this.dbInfo = dbInfo;
    }

    public static void main(String[] args) {
        new Runner(new DbInfo("jdbc:h2:~/h2testDb", "sa", "")).start();
    }

    public void start() {
        new Migrator(dbInfo, callbacks).migrate();
        int nbOfResults = 10;
        System.out.println("The first " + nbOfResults + " titles are:");
        new BookLookup(dbInfo).getBooksNames(0, nbOfResults).forEach(System.out::println);
    }
}
