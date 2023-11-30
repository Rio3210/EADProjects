package org.tech;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteEmployee")
public class DeleteEmployee extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int employeeId = Integer.parseInt(request.getParameter("id"));

        try (Connection connection = DBManager.getConnection()) {
            String deleteQuery = "DELETE FROM employee WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, employeeId);
                preparedStatement.executeUpdate();
            }

            // Display success message and redirect to viewEmployees.html
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Employee Deletion</title></head><body>");
            out.println("<h2>Employee successfully deleted!</h2>");
            out.println("<p>Redirecting to <a href='viewEmployees.html'>View Employees</a></p>");
            out.println("<script>setTimeout(function() { window.location.href = 'viewEmployees.html'; }, 3000);</script>");
            out.println("</body></html>");
        } catch (Exception e) {
            // Display failure message
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Employee Deletion</title></head><body>");
            out.println("<h2>Failed to delete employee.</h2>");
            out.println("<p>Please try again later.</p>");
            out.println("</body></html>");
        }
    }
}





//package org.tech;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@WebServlet("/DeleteEmployee")
//public class DeleteEmployee  extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        int employeeId = Integer.parseInt(request.getParameter("id"));
//
//        try (Connection connection = DBManager.getConnection()) {
//            String deleteQuery = "DELETE FROM employee WHERE id=?";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
//                preparedStatement.setInt(1, employeeId);
//                preparedStatement.executeUpdate();
//            }
//
//            response.sendRedirect("viewEmployees.html");
//        } catch (Exception e) {
//            throw new ServletException("Error deleting employee", e);
//        }
//    }
//}
