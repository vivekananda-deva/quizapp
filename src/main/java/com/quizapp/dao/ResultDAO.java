package com.quizapp.dao;

import com.quizapp.db.DatabaseConnection;
import com.quizapp.model.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultDAO {

    public void saveResult(Result result) throws SQLException {
        String sql = "INSERT INTO results (player_name, score, total) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, result.getPlayerName());
            ps.setInt(2, result.getScore());
            ps.setInt(3, result.getTotal());
            ps.executeUpdate();
        }
    }

    public List<Result> getTopResults(int limit) throws SQLException {
        List<Result> results = new ArrayList<>();
        String sql = "SELECT id, player_name, score, total, taken_at FROM results " +
                     "ORDER BY score DESC, taken_at ASC LIMIT ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(new Result(
                        rs.getInt("id"),
                        rs.getString("player_name"),
                        rs.getInt("score"),
                        rs.getInt("total"),
                        rs.getTimestamp("taken_at")
                    ));
                }
            }
        }
        return results;
    }
}
