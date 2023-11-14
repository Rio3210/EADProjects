package org.tech;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddEmployee")
public class AddEmployee extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("addEmployee.html").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String designation = request.getParameter("designation");
        double salary = Double.parseDouble(request.getParameter("salary"));

        try (Connection connection = DBManager.getConnection()) {
            String insertQuery = "INSERT INTO employee (name, designation, salary) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, designation);
                preparedStatement.setDouble(3, salary);
                preparedStatement.executeUpdate();
            }

            response.sendRedirect("viewEmployees.html");
        } catch (Exception e) {
            throw new ServletException("Error adding employee", e);
        }
    }
}
