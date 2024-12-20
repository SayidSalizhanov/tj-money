package ru.itis.tjmoney.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ConnectionManager {
    private static final String PROPERTIES_FILE = "database.properties";

    private static Connection connection;

    private ConnectionManager() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties properties = new Properties();
                try (InputStream input = ConnectionManager.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
                    if (input == null) {
                        throw new RuntimeException("Sorry, unable to find " + PROPERTIES_FILE);
                    }
                    properties.load(input);
                }
                String url = properties.getProperty("db.url");
                String username = properties.getProperty("db.username");
                String password = properties.getProperty("db.password");
                String driverPath = properties.getProperty("db.driver");

                Class.forName(driverPath);
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException | ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

//    public static Connection getConnectionNonSingleton() {
//        try {
//            Properties properties = new Properties();
//            try (InputStream input = ConnectionManager.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
//                if (input == null) {
//                    throw new RuntimeException("Sorry, unable to find " + PROPERTIES_FILE);
//                }
//                properties.load(input);
//            }
//            String url = properties.getProperty("db.url");
//            String username = properties.getProperty("db.username");
//            String password = properties.getProperty("db.password");
//            String driverPath = properties.getProperty("db.driver");
//
//            Class.forName(driverPath);
//            return DriverManager.getConnection(url, username, password);
//        } catch (SQLException | ClassNotFoundException | IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static Connection getConnectionNonSingleton() {
        String PROD_DB_HOST = System.getenv("PROD_DB_HOST");
        String PROD_DB_PORT = System.getenv("PROD_DB_PORT");
        String PROD_DB_PASSWORD = System.getenv("PROD_DB_PASSWORD");
        String PROD_DB_NAME = System.getenv("PROD_DB_NAME");
        String PROD_DB_USERNAME = System.getenv("PROD_DB_USERNAME");
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(
                    "jdbc:postgresql://%s:%s/%s".formatted(PROD_DB_HOST, PROD_DB_PORT, PROD_DB_NAME),
                    PROD_DB_USERNAME,
                    PROD_DB_PASSWORD
            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
