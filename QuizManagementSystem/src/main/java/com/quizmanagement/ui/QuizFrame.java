package com.quizmanagement.ui;

import com.quizmanagement.model.Question;
import com.quizmanagement.model.QuizResult;
import com.quizmanagement.service.QuizService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main Quiz taking screen.
 * Displays multiple choice questions with JRadioButton options in a scrollable view.
 */
public class QuizFrame extends JFrame {

    private final String studentName;
    private final QuizService quizService;
    private final List<Question> questions;
    private final Map<Integer, ButtonGroup> questionButtonGroups;

    public QuizFrame(String studentName, QuizService quizService) {
        this.studentName = studentName;
        this.quizService = quizService;
        this.questions = quizService.loadQuestions();
        this.questionButtonGroups = new HashMap<>();

        initComponents();
    }

    private void initComponents() {
        setTitle("Quiz Management System - Java Quiz");
        setSize(850, 700);
        setMinimumSize(new Dimension(650, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Java Knowledge Assessment Quiz");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        JLabel studentLabel = new JLabel("Student: " + studentName + "   |   Total Questions: " + questions.size());
        studentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        studentLabel.setForeground(new Color(236, 240, 241));

        headerPanel.add(titleLabel);
        headerPanel.add(studentLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Questions Container (Scrollable)
        JPanel questionsPanel = new JPanel();
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
        questionsPanel.setBackground(new Color(245, 247, 250));
        questionsPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        if (questions.isEmpty()) {
            JLabel noQuestionsLabel = new JLabel("No questions available in the database.");
            noQuestionsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            questionsPanel.add(noQuestionsLabel);
        } else {
            int qIndex = 1;
            for (Question q : questions) {
                JPanel card = createQuestionCard(q, qIndex++);
                questionsPanel.add(card);
                questionsPanel.add(Box.createRigidArea(new Dimension(0, 15))); // spacing between cards
            }
        }

        JScrollPane scrollPane = new JScrollPane(questionsPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // Footer / Submission Panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        JButton submitButton = new JButton("Submit Quiz");
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        submitButton.setBackground(new Color(39, 174, 96));
        submitButton.setForeground(Color.WHITE);
        submitButton.setPreferredSize(new Dimension(160, 42));
        submitButton.setFocusPainted(false);
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitButton.addActionListener(e -> handleSubmitQuiz());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cancelButton.setBackground(new Color(231, 76, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setPreferredSize(new Dimension(100, 42));
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to cancel the quiz? Your answers will not be saved.",
                    "Cancel Quiz",
                    JOptionPane.YES_NO_OPTION
            );
            if (choice == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        footerPanel.add(cancelButton);
        footerPanel.add(submitButton);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createQuestionCard(Question q, int index) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 224, 230), 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Question Title and Text
        JLabel qNumberLabel = new JLabel("Question " + index);
        qNumberLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        qNumberLabel.setForeground(new Color(41, 128, 185));

        JLabel qTextLabel = new JLabel("<html><body style='width: 600px;'>" + q.getQuestionText() + "</body></html>");
        qTextLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        qTextLabel.setBorder(new EmptyBorder(5, 0, 10, 0));

        card.add(qNumberLabel);
        card.add(qTextLabel);

        // Radio Buttons for Options A, B, C, D
        ButtonGroup group = new ButtonGroup();

        JRadioButton rbA = new JRadioButton("A. " + q.getOptionA());
        rbA.setActionCommand("A");
        styleRadioButton(rbA);

        JRadioButton rbB = new JRadioButton("B. " + q.getOptionB());
        rbB.setActionCommand("B");
        styleRadioButton(rbB);

        JRadioButton rbC = new JRadioButton("C. " + q.getOptionC());
        rbC.setActionCommand("C");
        styleRadioButton(rbC);

        JRadioButton rbD = new JRadioButton("D. " + q.getOptionD());
        rbD.setActionCommand("D");
        styleRadioButton(rbD);

        group.add(rbA);
        group.add(rbB);
        group.add(rbC);
        group.add(rbD);

        questionButtonGroups.put(q.getId(), group);

        card.add(rbA);
        card.add(rbB);
        card.add(rbC);
        card.add(rbD);

        return card;
    }

    private void styleRadioButton(JRadioButton rb) {
        rb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rb.setBackground(Color.WHITE);
        rb.setFocusPainted(false);
        rb.setBorder(new EmptyBorder(4, 5, 4, 5));
    }

    private void handleSubmitQuiz() {
        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No questions to evaluate.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Confirmation Dialog
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to submit the quiz?",
                "Submit Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Collect student answers
        Map<Integer, String> selectedAnswers = new HashMap<>();
        for (Question q : questions) {
            ButtonGroup bg = questionButtonGroups.get(q.getId());
            if (bg != null && bg.getSelection() != null) {
                selectedAnswers.put(q.getId(), bg.getSelection().getActionCommand());
            } else {
                selectedAnswers.put(q.getId(), ""); // Unanswered question
            }
        }

        // Evaluate and save via QuizService
        QuizResult result = quizService.evaluateAndSaveQuiz(studentName, questions, selectedAnswers);

        // Display Result Dialog
        showResultDialog(result);

        // Close QuizFrame after submission
        dispose();
    }

    private void showResultDialog(QuizResult result) {
        String message = String.format(
                "<html><div style='text-align: center; font-family: Segoe UI; padding: 10px;'>" +
                        "<h2 style='color: #27ae60; margin: 0;'>Quiz Completed!</h2><br>" +
                        "<p style='font-size: 14px;'><b>Student:</b> %s</p>" +
                        "<p style='font-size: 14px;'><b>Score:</b> %d / %d</p>" +
                        "<p style='font-size: 14px;'><b>Percentage:</b> %.2f%%</p>" +
                        "<p style='font-size: 12px; color: #7f8c8d;'>Attempt Recorded at: %s</p>" +
                        "</div></html>",
                result.getStudentName(),
                result.getScore(),
                result.getTotalQuestions(),
                result.getPercentage(),
                result.getAttemptedAt()
        );

        JOptionPane.showMessageDialog(
                this,
                message,
                "Quiz Result",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
