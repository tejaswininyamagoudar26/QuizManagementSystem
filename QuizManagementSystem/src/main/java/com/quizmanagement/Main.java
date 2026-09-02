package com.quizmanagement;

import com.quizmanagement.ui.WelcomeFrame;

import javax.swing.*;

/**
 * Main entry point for the Quiz Management System.
 * Initializes the database schema & seed data, sets up the look and feel, and opens the WelcomeFrame.
 */
public class Main {

    public static void main(String[] args) {
        // Step 1: Initialize Database (creates tables and seeds sample questions if empty)
        System.out.println("Starting Quiz Management System...");
        DatabaseInitializer.initializeDatabase();

        // Step 2: Set System Look and Feel for native OS appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set system look and feel: " + e.getMessage());
        }

        // Step 3: Launch Graphical User Interface on Swing Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            WelcomeFrame welcomeFrame = new WelcomeFrame();
            welcomeFrame.setVisible(true);
            System.out.println("Quiz Management System GUI launched successfully.");
        });
    }
}
