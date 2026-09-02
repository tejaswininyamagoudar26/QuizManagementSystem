package com.quizmanagement.ui;

import com.quizmanagement.model.QuizResult;
import com.quizmanagement.service.QuizService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Screen / Dialog to display previously saved quiz results in a JTable.
 */
public class ResultsFrame extends JDialog {

    public ResultsFrame(JFrame parent, QuizService quizService) {
        super(parent, "Quiz Results History", true);
        initComponents(quizService);
    }

    private void initComponents(QuizService quizService) {
        setSize(700, 450);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185));
        JLabel titleLabel = new JLabel("Previous Quiz Attempts");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Table Columns
        String[] columnNames = {"ID", "Student Name", "Score", "Total", "Percentage", "Date & Time"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Table cells are read-only
            }
        };

        // Populate Table from Database
        List<QuizResult> results = quizService.getAllResults();
        if (results.isEmpty()) {
            // If no records yet
            tableModel.addRow(new Object[]{"-", "No quiz attempts found", "-", "-", "-", "-"});
        } else {
            for (QuizResult r : results) {
                tableModel.addRow(new Object[]{
                        r.getId(),
                        r.getStudentName(),
                        r.getScore(),
                        r.getTotalQuestions(),
                        String.format("%.2f%%", r.getPercentage()),
                        r.getAttemptedAt()
                });
            }
        }

        JTable table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(236, 240, 241));

        // Center align table contents
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (i != 1) { // Keep student name left-aligned for better readability
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Button Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 15));

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        closeButton.setBackground(new Color(52, 73, 94));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dispose());
        bottomPanel.add(closeButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }
}
