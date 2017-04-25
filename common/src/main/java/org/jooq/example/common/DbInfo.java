package org.jooq.example.common;

public class DbInfo {
    private final String dbUrl;
    private final String user;
    private final String password;

    public DbInfo(String dbUrl, String user, String password) {
        this.dbUrl = dbUrl;
        this.user = user;
        this.password = password;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
