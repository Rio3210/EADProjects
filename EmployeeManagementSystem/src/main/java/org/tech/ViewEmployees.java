package org.tech;



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ViewEmployees")
public class ViewEmployees extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = DBManager.getConnection()) {
            String selectQuery = "SELECT * FROM employee";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectQuery)) {

                while (resultSet.next()) {
                    Employee employee = new Employee();
                    employee.setId(resultSet.getInt("id"));
                    employee.setName(resultSet.getString("name"));
                    employee.setDesignation(resultSet.getString("designation"));
                    employee.setSalary(resultSet.getDouble("salary"));
                    employees.add(employee);
                }
            }

            // Manually construct JSON string
            StringBuilder jsonBuilder = new StringBuilder("[");
            for (Employee employee : employees) {
                jsonBuilder.append("{");
                jsonBuilder.append("\"id\":").append(employee.getId()).append(",");
                jsonBuilder.append("\"name\":\"").append(employee.getName()).append("\",");
                jsonBuilder.append("\"designation\":\"").append(employee.getDesignation()).append("\",");
                jsonBuilder.append("\"salary\":").append(employee.getSalary());
                jsonBuilder.append("},");
            }
            if (employees.size() > 0) {
                jsonBuilder.deleteCharAt(jsonBuilder.length() - 1); // Remove the trailing comma
            }
            jsonBuilder.append("]");

            // Set the content type to JSON
            response.setContentType("application/json");

            // Write the JSON data to the response
            try (PrintWriter out = response.getWriter()) {
                out.print(jsonBuilder.toString());
            }

            // Forward the request to viewEmployees.html
            request.getRequestDispatcher("viewEmployees.html").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Error retrieving employees", e);
        }
    }
}
