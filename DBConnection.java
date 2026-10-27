package com.StudentCourseSystem;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    private static HikariDataSource dataSource;

    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://localhost:3306/student_course_db");
            config.setUsername("root");
            config.setPassword("root"); // Update with your MySQL password
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);

            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            System.err.println("Connection Pool Initialization Failed: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("DataSource is not initialized.");
        }
        return dataSource.getConnection();
    }
}