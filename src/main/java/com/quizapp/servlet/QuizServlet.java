package com.quizapp.servlet;

import com.quizapp.dao.QuestionDAO;
import com.quizapp.model.Question;
import com.quizapp.util.HtmlUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class QuizServlet extends HttpServlet {

    private final QuestionDAO questionDAO = new QuestionDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name = req.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
            resp.sendRedirect("./");
            return;
        }

        List<Question> questions;
        try {
            questions = questionDAO.getAllQuestions();
        } catch (SQLException e) {
            throw new ServletException("Failed to load questions", e);
        }

        StringBuilder body = new StringBuilder();
        body.append("<div class=\"card\">");
        body.append("<h1>Quiz Time!</h1>");
        body.append("<p class=\"subtitle\">Good luck, ").append(HtmlUtil.escape(name)).append("!</p>");
        body.append("<form action=\"submit\" method=\"post\">");
        body.append("<input type=\"hidden\" name=\"name\" value=\"")
            .append(HtmlUtil.escape(name)).append("\">");

        int i = 1;
        for (Question q : questions) {
            body.append("<div class=\"question\">");
            body.append("<p class=\"q-text\">").append(i).append(". ")
                .append(HtmlUtil.escape(q.getQuestionText())).append("</p>");
            appendOption(body, q.getId(), "A", q.getOptionA());
            appendOption(body, q.getId(), "B", q.getOptionB());
            appendOption(body, q.getId(), "C", q.getOptionC());
            appendOption(body, q.getId(), "D", q.getOptionD());
            body.append("</div>");
            i++;
        }

        body.append("<button type=\"submit\">Submit Answers</button>");
        body.append("</form>");
        body.append("</div>");

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(HtmlUtil.page("Quiz", body.toString()));
    }

    private void appendOption(StringBuilder body, int qId, String value, String text) {
        String inputName = "q" + qId;
        String inputId = inputName + "_" + value;
        body.append("<label class=\"option\" for=\"").append(inputId).append("\">");
        body.append("<input type=\"radio\" id=\"").append(inputId).append("\" name=\"")
            .append(inputName).append("\" value=\"").append(value).append("\" required>");
        body.append(HtmlUtil.escape(text));
        body.append("</label>");
    }
}
