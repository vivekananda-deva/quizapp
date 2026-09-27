package com.quizapp.servlet;

import com.quizapp.dao.QuestionDAO;
import com.quizapp.dao.ResultDAO;
import com.quizapp.model.Question;
import com.quizapp.model.Result;
import com.quizapp.util.HtmlUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SubmitServlet extends HttpServlet {

    private final QuestionDAO questionDAO = new QuestionDAO();
    private final ResultDAO resultDAO = new ResultDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name = req.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
            name = "Anonymous";
        }

        List<Question> questions;
        try {
            questions = questionDAO.getAllQuestions();
        } catch (SQLException e) {
            throw new ServletException("Failed to load questions", e);
        }

        int score = 0;
        StringBuilder review = new StringBuilder();
        int i = 1;
        for (Question q : questions) {
            String submitted = req.getParameter("q" + q.getId());
            boolean correct = q.getCorrectOption().equalsIgnoreCase(submitted);
            if (correct) score++;

            review.append("<div class=\"question\">");
            review.append("<p class=\"q-text\">").append(i).append(". ")
                  .append(HtmlUtil.escape(q.getQuestionText())).append("</p>");
            review.append("<p class=\"").append(correct ? "correct" : "incorrect").append("\">");
            if (correct) {
                review.append("Correct");
            } else {
                review.append("Your answer: ")
                      .append(submitted == null ? "(none)" : HtmlUtil.escape(submitted))
                      .append(" &mdash; Correct answer: ")
                      .append(HtmlUtil.escape(q.getCorrectOption()));
            }
            review.append("</p>");
            review.append("</div>");
            i++;
        }

        int total = questions.size();

        try {
            resultDAO.saveResult(new Result(name, score, total));
        } catch (SQLException e) {
            throw new ServletException("Failed to save result", e);
        }

        StringBuilder body = new StringBuilder();
        body.append("<div class=\"card\">");
        body.append("<h1>Results</h1>");
        body.append("<p class=\"subtitle\">Nice work, ").append(HtmlUtil.escape(name)).append("!</p>");
        body.append("<div class=\"score-badge\">");
        body.append("<div class=\"num\">").append(score).append(" / ").append(total).append("</div>");
        body.append("<div class=\"label\">questions correct</div>");
        body.append("</div>");
        body.append(review);
        body.append("<div class=\"actions\">");
        body.append("<a class=\"btn\" href=\"./\">Play Again</a>");
        body.append("<a class=\"btn\" href=\"leaderboard\" style=\"background:#444;\">Leaderboard</a>");
        body.append("</div>");
        body.append("</div>");

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(HtmlUtil.page("Results", body.toString()));
    }
}
