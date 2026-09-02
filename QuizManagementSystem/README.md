# Quiz Management System

A beginner-friendly, standalone desktop assessment application developed in Java using **Java Swing**, **JDBC**, **SQLite**, and **Maven**.

---

## 1. Project Title
**Quiz Management System**

---

## 2. Problem Statement
Traditional educational assessment methods require substantial manual effort to create question papers, distribute them, collect answers, evaluate responses, calculate percentages, and maintain student result records. These manual workflows are time-consuming and susceptible to calculation and recording errors.

The **Quiz Management System** automates the end-to-end quiz process in a digital desktop environment. Students can enter their name, attempt multiple-choice questions (MCQs), submit their answers, and receive instant score and percentage feedback. The application automatically records attempt details in a local SQLite database for future retrieval and review.

---

## 3. Objectives
1. Provide a graphical user interface (GUI) using Java Swing for taking quizzes.
2. Automate answer checking, score tallying, and percentage calculation.
3. Persist quiz results (student name, score, total questions, percentage, timestamp) in SQLite.
4. Provide immediate feedback upon quiz submission.
5. Provide a historical result viewing screen using Swing `JTable`.
6. Demonstrate Object-Oriented Programming (OOP), MVC-style separation of concerns, and JDBC database access.

---

## 4. Technologies Used
* **Programming Language:** Java (JDK 17 or higher)
* **GUI Toolkit:** Java Swing (`JFrame`, `JPanel`, `JRadioButton`, `ButtonGroup`, `JTable`, `JOptionPane`)
* **Database Connectivity:** JDBC (Java Database Connectivity) with `PreparedStatement`
* **Embedded Database:** SQLite (`sqlite-jdbc` 3.45.1.0)
* **Build & Dependency Management:** Apache Maven

---

## 5. Major Modules
* **Module 1 — Student Module:** Captures the student's name, initiates the quiz session, presents questions, records option selections, and presents instant score reports.
* **Module 2 — Question Management Module:** Handles question retrieval from SQLite and initial automatic seeding of sample Java MCQs.
* **Module 3 — Evaluation Module:** Compares student selections with correct options, tallies scores, and calculates percentage accuracy.
* **Module 4 — Result Management Module:** Saves each student attempt to the database and retrieves historical attempts into a read-only `JTable`.
* **Module 5 — Database Module:** Manages the SQLite database file (`quiz_system.db`), creates tables (`questions`, `results`), and executes parameterized queries securely.

---

## 6. Features
* **Zero Configuration Database:** SQLite database and tables are created automatically on the first application launch.
* **Auto-Seeded Sample Data:** 10 curated Java MCQs are seeded automatically into the database.
* **Input Validation:** Prevents starting the quiz without entering a student name.
* **Submission Confirmation:** Displays a confirmation prompt (`Yes / No`) before grading.
* **Instant Feedback Dialog:** Shows student name, score (e.g., 7/10), percentage (e.g., 70.00%), and completion timestamp.
* **Result History Viewer:** Displays all past quiz attempts sorted by the most recent submission.
* **Clean Layered Architecture:** Clear separation between Model, DAO, Service, and UI layers.

---

## 7. Project Structure
```text
QuizManagementSystem/
│
├── pom.xml
├── README.md
│
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── quizmanagement/
        │           │
        │           ├── Main.java
        │           ├── DatabaseConnection.java
        │           ├── DatabaseInitializer.java
        │           ├── QuestionSeeder.java
        │           │
        │           ├── model/
        │           │   ├── Question.java
        │           │   └── QuizResult.java
        │           │
        │           ├── dao/
        │           │   ├── QuestionDAO.java
        │           │   └── ResultDAO.java
        │           │
        │           ├── service/
        │           │   └── QuizService.java
        │           │
        │           └── ui/
        │               ├── WelcomeFrame.java
        │               ├── QuizFrame.java
        │               └── ResultsFrame.java
        │
        └── resources/
            └── schema.sql
```

---

## 8. Database Structure

### Table: `questions`
| Column Name | Data Type | Constraint | Description |
| :--- | :--- | :--- | :--- |
| `id` | INTEGER | PRIMARY KEY AUTOINCREMENT | Unique Question ID |
| `question_text` | TEXT | NOT NULL | The text of the question |
| `option_a` | TEXT | NOT NULL | Option A text |
| `option_b` | TEXT | NOT NULL | Option B text |
| `option_c` | TEXT | NOT NULL | Option C text |
| `option_d` | TEXT | NOT NULL | Option D text |
| `correct_option` | TEXT | NOT NULL | Correct option key (`A`, `B`, `C`, or `D`) |

### Table: `results`
| Column Name | Data Type | Constraint | Description |
| :--- | :--- | :--- | :--- |
| `id` | INTEGER | PRIMARY KEY AUTOINCREMENT | Unique Result ID |
| `student_name` | TEXT | NOT NULL | Name of the student |
| `score` | INTEGER | NOT NULL | Number of correct answers |
| `total_questions` | INTEGER | NOT NULL | Total questions in the quiz |
| `percentage` | REAL | NOT NULL | Score percentage (e.g., 70.00) |
| `attempted_at` | TEXT | NOT NULL | Timestamp (`yyyy-MM-dd HH:mm:ss`) |

---

## 9. Installation & Prerequisites
1. **Java Development Kit (JDK):** Version 17 or higher.
2. **Apache Maven:** Version 3.8 or higher.
3. **Operating System:** Windows 10/11, macOS, or Linux.

---

## 10. How to Run

### Method 1: Using Windows Command Prompt / Terminal with Maven
Open Command Prompt in the project root folder:
```bash
# Compile the project
mvn clean compile

# Run the application
mvn exec:java
```

### Method 2: Running in IntelliJ IDEA
1. Open IntelliJ IDEA.
2. Click **File -> Open...** and select the `QuizManagementSystem` directory.
3. Allow IntelliJ to import dependencies from `pom.xml`.
4. Navigate to `src/main/java/com/quizmanagement/Main.java`.
5. Right-click `Main.java` and click **Run 'Main.main()'**.

### Method 3: Running in Eclipse IDE
1. Click **File -> Import... -> Existing Maven Projects**.
2. Select the `QuizManagementSystem` directory and click **Finish**.
3. Right-click on `Main.java` -> **Run As -> Java Application**.

---

## 11. How the Application Works
1. **Startup:** `Main.java` calls `DatabaseInitializer.initializeDatabase()`. If `quiz_system.db` does not exist, it creates the database and tables, and `QuestionSeeder` inserts 10 sample Java questions.
2. **Home Screen:** `WelcomeFrame` is displayed. The user inputs their name and clicks **Start Quiz** or clicks **View Results**.
3. **Taking the Quiz:** `QuizFrame` retrieves questions via `QuizService` and `QuestionDAO`, displaying questions with `JRadioButton` option groups.
4. **Grading & Persistence:** The student clicks **Submit Quiz** and confirms submission. `QuizService` evaluates answers, calculates the score and percentage, records the attempt via `ResultDAO`, and displays the score card dialog.
5. **Viewing Results:** Clicking **View Results** from the home screen opens `ResultsFrame` displaying all stored attempts in a `JTable`.

---

## 12. Future Enhancements
* User authentication and role-based login (Admin vs. Student).
* Admin portal for adding, updating, and deleting quiz questions.
* Timed examination countdown with auto-submission.
* Question randomization and category-wise quizzes (e.g., Core Java, OOP, DBMS).
* Exporting results to PDF and Excel formats.
* Leaderboard and analytics dashboard.

---

## 13. Expected Outcome
A fully operational, standalone desktop application that demonstrates OOP principles, database CRUD operations with JDBC, and graphical event-driven programming in Java Swing without requiring third-party server infrastructure.
