package com.services;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.*;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet Filter implementation class SessionFilter
 */
@WebFilter("/login")
public class SessionFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public SessionFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// place your code here
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
        	  session.invalidate(); 
        	}
        	session = ((HttpServletRequest) request).getSession(); 
        	session.setAttribute("email", "kevinayikson14@gmail.com");
        
     // Check if the request is for the login page
        String requestURI = httpRequest.getRequestURI();
        boolean isLoginPage = requestURI.endsWith("/login.html") || requestURI.endsWith("/login"); // Adjust accordingly if your login page has a different URI
        
        // If it's not the login page and user is not authenticated, redirect to login page
        if (!isLoginPage && (session == null || session.getAttribute("email") == null)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.html");
            return;
        }

        // If it's the login page, validate username and password
        if (isLoginPage && httpRequest.getMethod().equalsIgnoreCase("POST")) {
            String email = httpRequest.getParameter("email");
            String password = httpRequest.getParameter("password");

            // Perform simple validation (you should use more secure methods in production)
            if ("kevinayikson14@gmail.com".equals(email) && "kevin1234".equals(password)) {
                // Valid credentials, create session and redirect to home page
                session = httpRequest.getSession(true); // Create a new session if one doesn't exist
                session.setAttribute("email", email);
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/AdmissionUI.html");
                return;
            } else {
                // Invalid credentials, redirect back to login page with error message
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.html?error=invalid");
                return;
            }
        }

		// pass the request along the filter chain
		chain.doFilter(request, response);
        
        }
	

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
