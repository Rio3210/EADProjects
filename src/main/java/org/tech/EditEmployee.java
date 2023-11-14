package org.tech;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EditEmployee")
public class EditEmployee extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int employeeId = Integer.parseInt(request.getParameter("id"));

        try (Connection connection = DBManager.getConnection()) {
            String selectQuery = "SELECT * FROM employee WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, employeeId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Employee employee = new Employee();
                        employee.setId(resultSet.getInt("id"));
                        employee.setName(resultSet.getString("name"));
                        employee.setDesignation(resultSet.getString("designation"));
                        employee.setSalary(resultSet.getDouble("salary"));
                        request.setAttribute("employee", employee);
                    }
                }
            }

            request.getRequestDispatcher("editEmployee.html").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error retrieving employee for editing", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int employeeId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String designation = request.getParameter("designation");
        double salary = Double.parseDouble(request.getParameter("salary"));

        try (Connection connection = DBManager.getConnection()) {
            String updateQuery = "UPDATE employee SET name=?, designation=?, salary=? WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, designation);
                preparedStatement.setDouble(3, salary);
                preparedStatement.setInt(4, employeeId);
                preparedStatement.executeUpdate();
            }

            response.sendRedirect("viewEmployees.html");
        } catch (Exception e) {
            throw new ServletException("Error updating employee", e);
        }
    }
}
