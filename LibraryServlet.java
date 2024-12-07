/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuler_durden.clientgui;

/**
 *
 * @author Admin
 */


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "0000";

    
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("listBooks".equals(action)) {
            listBooks(response);
        } else if ("borrowBook".equals(action)) {
            borrowBook(request, response);
        }
    }

    private void listBooks(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>Available Books</h1>");
        out.println("<ul>");

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Books WHERE available = TRUE");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                out.println("<li>" + resultSet.getString("title") + " by " + resultSet.getString("author") + "</li>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<li>Error: " + e.getMessage() + "</li>");
        }

        out.println("</ul>");
    }

    private void borrowBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String bookId = request.getParameter("book_id");
        String userId = request.getParameter("user_id");

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Update book availability
            try (PreparedStatement updateBookStatement = connection.prepareStatement("UPDATE Books SET available = FALSE WHERE book_id = ?")) {
                updateBookStatement.setString(1, bookId);
                updateBookStatement.executeUpdate();
            }

            // Insert loan record
            try (PreparedStatement insertLoanStatement = connection.prepareStatement("INSERT INTO Loans (user_id, book_id, loan_date) VALUES (?, ?, CURRENT_DATE)")) {
                insertLoanStatement.setString(1, userId);
                insertLoanStatement.setString(2, bookId);
                insertLoanStatement.executeUpdate();
            }

            response.setContentType("text/plain");
            response.getWriter().println("Book borrowed successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            response.setContentType("text/plain");
            response.getWriter().println("Failed to borrow book.");
        }
    }
}
