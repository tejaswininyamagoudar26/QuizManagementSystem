package com.quizmanagement.dao;

import com.quizmanagement.DatabaseConnection;
import com.quizmanagement.model.QuizResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for results table.
 * Handles inserting student quiz attempts and retrieving result history.
 */
public class ResultDAO {

    /**
     * Saves a quiz result into the database.
     *
     * @param result QuizResult object containing attempt details
     * @return true if saved successfully, false otherwise
     */
    public boolean saveResult(QuizResult result) {
        String sql = "INSERT INTO results (student_name, score, total_questions, percentage, attempted_at) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, result.getStudentName());
            stmt.setInt(2, result.getScore());
            stmt.setInt(3, result.getTotalQuestions());
            stmt.setDouble(4, result.getPercentage());
            stmt.setString(5, result.getAttemptedAt());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saving quiz result: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves all saved quiz results, ordered with newest attempts first.
     *
     * @return List of QuizResult objects
     */
    public List<QuizResult> getAllResults() {
        List<QuizResult> results = new ArrayList<>();
        String sql = "SELECT id, student_name, score, total_questions, percentage, attempted_at FROM results ORDER BY id DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                QuizResult r = new QuizResult(
                        rs.getInt("id"),
                        rs.getString("student_name"),
                        rs.getInt("score"),
                        rs.getInt("total_questions"),
                        rs.getDouble("percentage"),
                        rs.getString("attempted_at")
                );
                results.add(r);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching results: " + e.getMessage());
        }
        return results;
    }
}
