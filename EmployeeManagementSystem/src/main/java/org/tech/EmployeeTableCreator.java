package org.tech;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeTableCreator {

    public static void createEmployeeTable() {
        try (Connection connection = DBManager.getConnection()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS employee (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL," +
                    "designation VARCHAR(255) NOT NULL," +
                    "salary DECIMAL(10,2) NOT NULL" +
                    ")";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
                preparedStatement.execute();
                System.out.println("Employee table created successfully.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create employee table.", e);
        }
    }

    public static void main(String[] args) {
        createEmployeeTable();
    }
}
