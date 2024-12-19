package ru.itis.tjmoney.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/tj-money";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    private static Connection connection;

    private ConnectionManager() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
}
