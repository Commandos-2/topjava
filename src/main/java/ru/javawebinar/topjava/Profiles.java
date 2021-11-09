package ru.javawebinar.topjava;

import org.springframework.util.ClassUtils;

public class Profiles {
    public static final String
            JDBC = "jdbc",
            JPA = "jpa",
            DATAJPA = "datajpa";

    public static final String REPOSITORY_IMPLEMENTATION_JPA = JPA;
    public static final String REPOSITORY_IMPLEMENTATION_DATA = DATAJPA;
    public static final String REPOSITORY_IMPLEMENTATION_JDBC = JDBC;
    public static final String POSTGRES_DB = "postgres";
    public static final String HSQL_DB = "hsqldb";
    public static String ACTIVE_DB = POSTGRES_DB;

    //  Get DB profile depending of DB driver in classpath
    public static String[] getActiveDbProfile() {
        if (ClassUtils.isPresent("org.postgresql.Driver", null)) {
            ACTIVE_DB = POSTGRES_DB;
        } else if (ClassUtils.isPresent("org.hsqldb.jdbcDriver", null)) {
            ACTIVE_DB = HSQL_DB;
        } else {
            throw new IllegalStateException("Could not find DB driver");
        }
        return new String[]{ACTIVE_DB, REPOSITORY_IMPLEMENTATION_JPA};
    }
}