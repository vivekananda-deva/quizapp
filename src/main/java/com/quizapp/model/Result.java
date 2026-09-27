package com.quizapp.model;

import java.sql.Timestamp;

public class Result {
    private int id;
    private final String playerName;
    private final int score;
    private final int total;
    private Timestamp takenAt;

    public Result(String playerName, int score, int total) {
        this.playerName = playerName;
        this.score = score;
        this.total = total;
    }

    public Result(int id, String playerName, int score, int total, Timestamp takenAt) {
        this.id = id;
        this.playerName = playerName;
        this.score = score;
        this.total = total;
        this.takenAt = takenAt;
    }

    public int getId() { return id; }
    public String getPlayerName() { return playerName; }
    public int getScore() { return score; }
    public int getTotal() { return total; }
    public Timestamp getTakenAt() { return takenAt; }
}
