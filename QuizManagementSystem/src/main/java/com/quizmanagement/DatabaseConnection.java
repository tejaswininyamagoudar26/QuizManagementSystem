package com.quizmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages database connectivity with the local SQLite database.
 * Uses the Singleton pattern style to provide JDBC connections.
 */
public class DatabaseConnection {

    // SQLite database file name located in the application root directory
    private static final String DB_URL = "jdbc:sqlite:quiz_system.db";

    /**
     * Obtains and returns a new JDBC Connection to SQLite database.
     *
     * @return Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load the SQLite JDBC driver class
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC Driver not found: " + e.getMessage());
        }
        return DriverManager.getConnection(DB_URL);
    }
}
