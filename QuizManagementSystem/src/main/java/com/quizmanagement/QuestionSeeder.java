package com.quizmanagement;

import com.quizmanagement.dao.QuestionDAO;
import com.quizmanagement.model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Seeds initial sample Java questions into SQLite if the questions table is empty.
 */
public class QuestionSeeder {

    /**
     * Checks if questions table has data. If empty, inserts 10 sample Java MCQs.
     */
    public static void seedQuestionsIfEmpty() {
        QuestionDAO questionDAO = new QuestionDAO();
        if (questionDAO.getQuestionCount() == 0) {
            System.out.println("Questions table is empty. Seeding sample Java questions...");
            List<Question> sampleQuestions = getSampleQuestions();
            for (Question q : sampleQuestions) {
                questionDAO.insertQuestion(q);
            }
            System.out.println("Seeded " + sampleQuestions.size() + " sample questions successfully.");
        } else {
            System.out.println("Questions already exist in the database. Skipping seeding.");
        }
    }

    /**
     * Returns a curated list of 10 beginner-friendly Java MCQs.
     *
     * @return List of Question objects
     */
    private static List<Question> getSampleQuestions() {
        List<Question> list = new ArrayList<>();

        list.add(new Question(
                "Which keyword is used to inherit a class in Java?",
                "implements",
                "extends",
                "inherits",
                "super",
                "B"
        ));

        list.add(new Question(
                "Which of the following is NOT a Java primitive data type?",
                "int",
                "boolean",
                "String",
                "char",
                "C"
        ));

        list.add(new Question(
                "Which access specifier makes a class member visible only within its own class?",
                "public",
                "protected",
                "default",
                "private",
                "D"
        ));

        list.add(new Question(
                "What is the default value of an uninitialized boolean variable in Java?",
                "true",
                "false",
                "null",
                "0",
                "B"
        ));

        list.add(new Question(
                "Which concept of OOP is demonstrated by having multiple methods with the same name but different parameters?",
                "Method Overriding",
                "Method Overloading",
                "Encapsulation",
                "Abstraction",
                "B"
        ));

        list.add(new Question(
                "Which interface must be implemented to create a thread by implementing an interface?",
                "Runnable",
                "Threadable",
                "Callable",
                "Processable",
                "A"
        ));

        list.add(new Question(
                "Which of these statements about constructors in Java is TRUE?",
                "Constructors must have a return type.",
                "Constructors have the same name as the class.",
                "Constructors cannot be overloaded.",
                "Constructors are called using the delete keyword.",
                "B"
        ));

        list.add(new Question(
                "Which package is imported by default in every Java program?",
                "java.util",
                "java.io",
                "java.lang",
                "java.net",
                "C"
        ));

        list.add(new Question(
                "In JDBC, which interface is used to execute parameterized SQL queries securely?",
                "Statement",
                "PreparedStatement",
                "CallableStatement",
                "ResultSet",
                "B"
        ));

        list.add(new Question(
                "Which keyword is used to prevent method overriding and inheritance in Java?",
                "static",
                "abstract",
                "const",
                "final",
                "D"
        ));

        return list;
    }
}
