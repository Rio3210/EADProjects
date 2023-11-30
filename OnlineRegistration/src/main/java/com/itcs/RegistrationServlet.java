package com.itcs;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class LoginServlet
 */
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
	    String name = req.getParameter("name");
	    String email = req.getParameter("email");
	    String password = req.getParameter("password");

	    try (Connection conn = DBManager.getConnection()) {
	        String query = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
	        PreparedStatement pstmt = conn.prepareStatement(query);

	        pstmt.setString(1, name);
	        pstmt.setString(2, email);
	        pstmt.setString(3, password);
	        pstmt.executeUpdate();

	        response.sendRedirect("confirmation.jsp");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}