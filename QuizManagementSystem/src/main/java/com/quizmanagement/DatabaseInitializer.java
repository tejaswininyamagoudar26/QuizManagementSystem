package com.quizmanagement;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

/**
 * Automatically initializes the SQLite database schema and triggers question seeding.
 */
public class DatabaseInitializer {

    /**
     * Initializes database tables using schema.sql and seeds initial data.
     */
    public static void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Read schema.sql from classpath resources
            InputStream in = DatabaseInitializer.class.getClassLoader().getResourceAsStream("schema.sql");
            if (in != null) {
                String schemaSql = new BufferedReader(new InputStreamReader(in))
                        .lines()
                        .collect(Collectors.joining("\n"));

                // Split statements by semicolon and execute each
                String[] queries = schemaSql.split(";");
                for (String query : queries) {
                    String trimmed = query.trim();
                    if (!trimmed.isEmpty()) {
                        stmt.execute(trimmed);
                    }
                }
                System.out.println("Database tables checked/created successfully from schema.sql.");
            } else {
                // Fallback direct creation if resource stream is unavailable
                stmt.execute("CREATE TABLE IF NOT EXISTS questions (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "question_text TEXT NOT NULL, " +
                        "option_a TEXT NOT NULL, " +
                        "option_b TEXT NOT NULL, " +
                        "option_c TEXT NOT NULL, " +
                        "option_d TEXT NOT NULL, " +
                        "correct_option TEXT NOT NULL)");

                stmt.execute("CREATE TABLE IF NOT EXISTS results (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "student_name TEXT NOT NULL, " +
                        "score INTEGER NOT NULL, " +
                        "total_questions INTEGER NOT NULL, " +
                        "percentage REAL NOT NULL, " +
                        "attempted_at TEXT NOT NULL)");

                System.out.println("Database tables checked/created successfully using fallback statements.");
            }

            // Seed sample questions if questions table is empty
            QuestionSeeder.seedQuestionsIfEmpty();

        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }
}
