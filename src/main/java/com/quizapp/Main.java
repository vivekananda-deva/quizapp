package com.quizapp;

import com.quizapp.db.DBInitializer;
import com.quizapp.servlet.HomeServlet;
import com.quizapp.servlet.LeaderboardServlet;
import com.quizapp.servlet.QuizServlet;
import com.quizapp.servlet.SubmitServlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Application entry point. Boots the database schema, then starts an
 * embedded Jetty server on the port Render (or the environment) provides.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Initializing database...");
        DBInitializer.initialize();
        System.out.println("Database ready.");

        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));

        Server server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new HomeServlet()), "/");
        context.addServlet(new ServletHolder(new QuizServlet()), "/quiz");
        context.addServlet(new ServletHolder(new SubmitServlet()), "/submit");
        context.addServlet(new ServletHolder(new LeaderboardServlet()), "/leaderboard");

        server.start();
        System.out.println("Quiz app running on port " + port);
        server.join();
    }
}
