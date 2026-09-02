package com.quizmanagement.service;

import com.quizmanagement.dao.QuestionDAO;
import com.quizmanagement.dao.ResultDAO;
import com.quizmanagement.model.Question;
import com.quizmanagement.model.QuizResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Service class implementing business logic for quiz evaluation and results management.
 * Acts as an intermediary between the UI and the DAO layer.
 */
public class QuizService {

    private final QuestionDAO questionDAO;
    private final ResultDAO resultDAO;

    public QuizService() {
        this.questionDAO = new QuestionDAO();
        this.resultDAO = new ResultDAO();
    }

    /**
     * Loads all questions available in the system.
     *
     * @return List of Question objects
     */
    public List<Question> loadQuestions() {
        return questionDAO.getAllQuestions();
    }

    /**
     * Evaluates the student's selected answers against correct options in database,
     * calculates score and percentage, and persists the result.
     *
     * @param studentName    Name of the student
     * @param questions      List of questions presented in the quiz
     * @param selectedAnswers Map of Question ID to selected option ("A", "B", "C", "D" or empty)
     * @return Saved QuizResult object containing final calculated stats
     */
    public QuizResult evaluateAndSaveQuiz(String studentName, List<Question> questions, Map<Integer, String> selectedAnswers) {
        int score = 0;
        int totalQuestions = questions.size();

        for (Question q : questions) {
            String studentChoice = selectedAnswers.get(q.getId());
            if (studentChoice != null && studentChoice.trim().equalsIgnoreCase(q.getCorrectOption().trim())) {
                score++;
            }
        }

        double percentage = (totalQuestions > 0) ? ((double) score / totalQuestions) * 100.0 : 0.0;

        // Capture current date and time
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        QuizResult result = new QuizResult(studentName, score, totalQuestions, percentage, timestamp);
        resultDAO.saveResult(result);

        return result;
    }

    /**
     * Retrieves all saved quiz results.
     *
     * @return List of QuizResult objects
     */
    public List<QuizResult> getAllResults() {
        return resultDAO.getAllResults();
    }
}
