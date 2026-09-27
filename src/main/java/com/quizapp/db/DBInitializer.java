package com.quizapp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Creates the schema on startup (if it doesn't already exist) and seeds
 * the questions table with a starter set of general knowledge / science
 * trivia the first time the app runs against a fresh database.
 */
public class DBInitializer {

    public static void initialize() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS questions (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "question_text TEXT NOT NULL," +
                "option_a TEXT NOT NULL," +
                "option_b TEXT NOT NULL," +
                "option_c TEXT NOT NULL," +
                "option_d TEXT NOT NULL," +
                "correct_option CHAR(1) NOT NULL" +
                ")"
            );

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS results (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "player_name VARCHAR(100) NOT NULL," +
                "score INT NOT NULL," +
                "total INT NOT NULL," +
                "taken_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")"
            );

            seedQuestions(conn);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    private static void seedQuestions(Connection conn) throws SQLException {
        int count = 0;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM questions")) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        }

        if (count > 0) {
            return; // already seeded
        }

        String[][] questions = {
            {"What is the chemical symbol for gold?", "Ag", "Au", "Gd", "Go", "B"},
            {"Which planet is known as the Red Planet?", "Venus", "Jupiter", "Mars", "Saturn", "C"},
            {"Who wrote the play 'Romeo and Juliet'?", "Charles Dickens", "William Shakespeare", "Mark Twain", "Jane Austen", "B"},
            {"What is the largest ocean on Earth?", "Atlantic Ocean", "Indian Ocean", "Arctic Ocean", "Pacific Ocean", "D"},
            {"How many continents are there on Earth?", "5", "6", "7", "8", "C"},
            {"What is the powerhouse of the cell?", "Nucleus", "Ribosome", "Mitochondria", "Golgi body", "C"},
            {"Which language is primarily spoken in Brazil?", "Spanish", "Portuguese", "French", "Italian", "B"},
            {"What is the smallest prime number?", "0", "1", "2", "3", "C"},
            {"Which country is home to the kangaroo in the wild?", "South Africa", "India", "Australia", "Brazil", "C"},
            {"What does 'HTTP' stand for?", "HyperText Transfer Protocol", "High Transfer Text Protocol", "HyperText Type Protocol", "Home Tool Transfer Protocol", "A"}
        };

        String insertSql = "INSERT INTO questions " +
                "(question_text, option_a, option_b, option_c, option_d, correct_option) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
            for (String[] q : questions) {
                ps.setString(1, q[0]);
                ps.setString(2, q[1]);
                ps.setString(3, q[2]);
                ps.setString(4, q[3]);
                ps.setString(5, q[4]);
                ps.setString(6, q[5]);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
