package com.quizapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Resolves JDBC connection details for MySQL.
 *
 * For local development (e.g. running from Eclipse against a local
 * MySQL / XAMPP install), it uses JDBC_URL / DB_USER / DB_PASSWORD env
 * vars if set, otherwise falls back to sane localhost defaults:
 *
 *   jdbc:mysql://localhost:3306/quizapp   user: root   password: (empty)
 *
 * If your local MySQL uses a different username/password (e.g. a
 * standalone MySQL install usually has a root password you set during
 * installation, while XAMPP's default is root / no password), either
 * set the DB_USER / DB_PASSWORD env vars, or edit the defaults below.
 */
public class DatabaseConnection {

    private static String jdbcUrl;
    private static String user;
    private static String password;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC driver not found", e);
        }
        configure();
    }

    private static void configure() {
        jdbcUrl = System.getenv().getOrDefault(
            "JDBC_URL",
            "jdbc:mysql://localhost:3306/quizapp?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
        );
        user = System.getenv().getOrDefault("DB_USER", "root");
        password = System.getenv().getOrDefault("DB_PASSWORD", "");
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, user, password);
    }
}
