package org.tech;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteEmployee")
public class DeleteEmployee  extends HttpServlet {
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

            response.sendRedirect("viewEmployees.html");
        } catch (Exception e) {
            throw new ServletException("Error deleting employee", e);
        }
    }
}
