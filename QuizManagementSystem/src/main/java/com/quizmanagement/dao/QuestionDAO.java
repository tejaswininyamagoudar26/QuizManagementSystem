package com.quizmanagement.dao;

import com.quizmanagement.DatabaseConnection;
import com.quizmanagement.model.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for questions table.
 * Handles database operations such as retrieving and inserting questions.
 */
public class QuestionDAO {

    /**
     * Retrieves all questions from the database.
     *
     * @return List of Question objects
     */
    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT id, question_text, option_a, option_b, option_c, option_d, correct_option FROM questions ORDER BY id ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Question q = new Question(
                        rs.getInt("id"),
                        rs.getString("question_text"),
                        rs.getString("option_a"),
                        rs.getString("option_b"),
                        rs.getString("option_c"),
                        rs.getString("option_d"),
                        rs.getString("correct_option")
                );
                questions.add(q);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching questions: " + e.getMessage());
        }
        return questions;
    }

    /**
     * Inserts a single question into the database.
     *
     * @param question Question object to insert
     * @return true if inserted successfully, false otherwise
     */
    public boolean insertQuestion(Question question) {
        String sql = "INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_option) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, question.getQuestionText());
            stmt.setString(2, question.getOptionA());
            stmt.setString(3, question.getOptionB());
            stmt.setString(4, question.getOptionC());
            stmt.setString(5, question.getOptionD());
            stmt.setString(6, question.getCorrectOption());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting question: " + e.getMessage());
            return false;
        }
    }

    /**
     * Checks the total count of questions in the database.
     *
     * @return count of questions
     */
    public int getQuestionCount() {
        String sql = "SELECT COUNT(*) AS total FROM questions";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error getting question count: " + e.getMessage());
        }
        return 0;
    }
}
