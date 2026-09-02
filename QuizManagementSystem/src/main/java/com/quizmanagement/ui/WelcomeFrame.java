package com.quizmanagement.ui;

import com.quizmanagement.service.QuizService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Welcome / Home Screen of the Quiz Management System.
 * Allows entering student name to start a quiz or viewing past quiz results.
 */
public class WelcomeFrame extends JFrame {

    private JTextField nameTextField;
    private final QuizService quizService;

    public WelcomeFrame() {
        this.quizService = new QuizService();
        initComponents();
    }

    private void initComponents() {
        setTitle("Quiz Management System");
        setSize(550, 480);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Header Banner
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setBorder(new EmptyBorder(25, 20, 20, 20));

        JLabel appTitle = new JLabel("QUIZ MANAGEMENT SYSTEM", SwingConstants.CENTER);
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        appTitle.setForeground(Color.WHITE);

        JLabel appSubtitle = new JLabel("Automated Assessment & Instant Evaluation", SwingConstants.CENTER);
        appSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        appSubtitle.setForeground(new Color(236, 240, 241));

        headerPanel.add(appTitle);
        headerPanel.add(appSubtitle);
        add(headerPanel, BorderLayout.NORTH);

        // Center Form Card
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 247, 250));

        JPanel formCard = new JPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 224, 230), 1, true),
                new EmptyBorder(30, 35, 30, 35)
        ));

        JLabel nameLabel = new JLabel("Enter Student Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLabel.setForeground(new Color(44, 62, 80));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameTextField = new JTextField();
        nameTextField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        nameTextField.setMaximumSize(new Dimension(320, 38));
        nameTextField.setPreferredSize(new Dimension(320, 38));
        nameTextField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(189, 195, 199), 1, true),
                new EmptyBorder(5, 10, 5, 10)
        ));

        // Start Quiz Button
        JButton startButton = new JButton("Start Quiz");
        startButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        startButton.setBackground(new Color(41, 128, 185));
        startButton.setForeground(Color.WHITE);
        startButton.setMaximumSize(new Dimension(320, 42));
        startButton.setPreferredSize(new Dimension(320, 42));
        startButton.setFocusPainted(false);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> handleStartQuiz());

        // View Results Button
        JButton resultsButton = new JButton("View Results");
        resultsButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        resultsButton.setBackground(new Color(52, 73, 94));
        resultsButton.setForeground(Color.WHITE);
        resultsButton.setMaximumSize(new Dimension(320, 42));
        resultsButton.setPreferredSize(new Dimension(320, 42));
        resultsButton.setFocusPainted(false);
        resultsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        resultsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultsButton.addActionListener(e -> handleViewResults());

        formCard.add(nameLabel);
        formCard.add(Box.createRigidArea(new Dimension(0, 10)));
        formCard.add(nameTextField);
        formCard.add(Box.createRigidArea(new Dimension(0, 20)));
        formCard.add(startButton);
        formCard.add(Box.createRigidArea(new Dimension(0, 12)));
        formCard.add(resultsButton);

        centerPanel.add(formCard);
        add(centerPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(245, 247, 250));
        footerPanel.setBorder(new EmptyBorder(10, 10, 15, 10));
        JLabel footerLabel = new JLabel("MCA Academic Project | Java Swing & SQLite");
        footerLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        footerLabel.setForeground(new Color(127, 140, 141));
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private void handleStartQuiz() {
        String studentName = nameTextField.getText().trim();

        // Validation: Student name must not be empty
        if (studentName.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your name.",
                    "Input Validation",
                    JOptionPane.WARNING_MESSAGE
            );
            nameTextField.requestFocus();
            return;
        }

        // Open QuizFrame
        QuizFrame quizFrame = new QuizFrame(studentName, quizService);
        quizFrame.setVisible(true);
    }

    private void handleViewResults() {
        ResultsFrame resultsFrame = new ResultsFrame(this, quizService);
        resultsFrame.setVisible(true);
    }
}
