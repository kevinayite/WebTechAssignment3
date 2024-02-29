package com.services;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.Session;
import javax.mail.*;

import javax.mail.internet.*;

@WebServlet("/submitAdmission")
public class AdmissionServlet extends HttpServlet{
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String nom = request.getParameter("names");
		String email = (String)request.getSession().getAttribute("email");
		
		
		System.out.println("Names of the Student "+nom);
		System.out.println("Email of the studdent " +email);
		
		

        // Gmail credentials
        final String username = "kevinayikson14@gmail.com";
        final String password = "irrx aqdm rxvn cnom";

        // Create email properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        

        // Create session
        Session mailSession = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create message
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Admission Submission Confirmation");
            message.setText("Dear " + nom + ",\n\n"
                    + "Thank you for submitting your admission request. We have received your application.\n\n"
                    + "Best regards,\n"
                    + "AUCA Registration");
            
            message.setText("Thank you for submitting your admission request. We have received your application.\n\n"
                    + "Best regards,\n"
                    + "Your School");

            // Send message
            Transport.send(message);

            // Set a success message
            request.getSession().setAttribute("message", "Admission Submitted Successfully");
        } catch (MessagingException e) {
            // Set an error message
            request.getSession().setAttribute("message", "Error Sending Email. Please contact support.");

            // Log the exception
            e.printStackTrace();
        }

        // Redirect to result.jsp
        response.sendRedirect("result.html");
	}

}
