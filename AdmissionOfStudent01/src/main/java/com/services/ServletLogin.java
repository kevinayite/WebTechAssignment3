package com.services;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ServletLogin extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        PrintWriter out = res.getWriter();
        
        if (email != null && password != null) {
            if (email.equalsIgnoreCase("kevinayikson14@gmail.com") && password.equals("kevin1234")) {
                RequestDispatcher rd = req.getRequestDispatcher("/AdmissionUI.html");
                rd.forward(req, res);
            } else {
                res.setContentType("text/html");
                out.println("Your username or password is incorrect");
                RequestDispatcher rd1 = req.getRequestDispatcher("/login.html");
                rd1.include(req, res);
            }
        }
    }
}
