package com.quizmanagement.model;

/**
 * Model class representing a Student's Quiz Result.
 * Demonstrates Encapsulation with private fields and public getters/setters.
 */
public class QuizResult {
    private int id;
    private String studentName;
    private int score;
    private int totalQuestions;
    private double percentage;
    private String attemptedAt;

    // Default Constructor
    public QuizResult() {
    }

    // Parameterized Constructor without ID (useful for inserting new results)
    public QuizResult(String studentName, int score, int totalQuestions, double percentage, String attemptedAt) {
        this.studentName = studentName;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.percentage = percentage;
        this.attemptedAt = attemptedAt;
    }

    // Full Parameterized Constructor
    public QuizResult(int id, String studentName, int score, int totalQuestions, double percentage, String attemptedAt) {
        this.id = id;
        this.studentName = studentName;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.percentage = percentage;
        this.attemptedAt = attemptedAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getAttemptedAt() {
        return attemptedAt;
    }

    public void setAttemptedAt(String attemptedAt) {
        this.attemptedAt = attemptedAt;
    }
}
