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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//@WebServlet("/search")
public class Server extends HttpServlet {
    private SearchManager searchManager = new SearchManager();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("query");
        List<String> results = searchManager.searchBooks(query);

        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();
        for (String result : results) {
            out.println(result);
        }
    }
}
