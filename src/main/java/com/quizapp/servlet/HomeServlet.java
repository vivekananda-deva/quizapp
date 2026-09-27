package com.quizapp.servlet;

import com.quizapp.util.HtmlUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String body =
            "<div class=\"card\">" +
            "<h1>Science &amp; General Knowledge Quiz</h1>" +
            "<p class=\"subtitle\">10 quick questions. Enter your name to begin.</p>" +
            "<form action=\"quiz\" method=\"get\">" +
            "<input type=\"text\" name=\"name\" placeholder=\"Your name\" required maxlength=\"50\">" +
            "<button type=\"submit\">Start Quiz</button>" +
            "</form>" +
            "<div class=\"actions\">" +
            "<a class=\"btn\" href=\"leaderboard\" style=\"background:#444;\">View Leaderboard</a>" +
            "</div>" +
            "</div>";

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(HtmlUtil.page("Quiz App", body));
    }
}
