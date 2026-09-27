package com.quizapp.servlet;

import com.quizapp.dao.ResultDAO;
import com.quizapp.model.Result;
import com.quizapp.util.HtmlUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LeaderboardServlet extends HttpServlet {

    private final ResultDAO resultDAO = new ResultDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Result> results;
        try {
            results = resultDAO.getTopResults(10);
        } catch (SQLException e) {
            throw new ServletException("Failed to load leaderboard", e);
        }

        StringBuilder body = new StringBuilder();
        body.append("<div class=\"card\">");
        body.append("<h1>Leaderboard</h1>");
        body.append("<p class=\"subtitle\">Top 10 scores</p>");

        if (results.isEmpty()) {
            body.append("<p>No one has taken the quiz yet. Be the first!</p>");
        } else {
            body.append("<table><thead><tr><th>#</th><th>Name</th><th>Score</th></tr></thead><tbody>");
            int rank = 1;
            for (Result r : results) {
                String rankClass = rank == 1 ? "rank-1" : rank == 2 ? "rank-2" : rank == 3 ? "rank-3" : "";
                body.append("<tr>");
                body.append("<td class=\"").append(rankClass).append("\">").append(rank).append("</td>");
                body.append("<td>").append(HtmlUtil.escape(r.getPlayerName())).append("</td>");
                body.append("<td>").append(r.getScore()).append(" / ").append(r.getTotal()).append("</td>");
                body.append("</tr>");
                rank++;
            }
            body.append("</tbody></table>");
        }

        body.append("<div class=\"actions\">");
        body.append("<a class=\"btn\" href=\"./\">Back to Home</a>");
        body.append("</div>");
        body.append("</div>");

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(HtmlUtil.page("Leaderboard", body.toString()));
    }
}
