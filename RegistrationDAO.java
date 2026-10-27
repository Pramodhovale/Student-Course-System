package com.StudentCourseSystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class RegistrationDAO {

        public void registerStudent(Connection conn, Student student) throws SQLException {
            String sql = "INSERT INTO students (name, email, phone) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, student.getName());
                pstmt.setString(2, student.getEmail());
                pstmt.setString(3, student.getPhone());
                pstmt.executeUpdate();
            }
        }

        public void addCourse(Connection conn, Course course) throws SQLException {
            String sql = "INSERT INTO courses (course_name, credits) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, course.getCourseName());
                pstmt.setInt(2, course.getCredits());
                pstmt.executeUpdate();
            }
        }

        public void enrollStudent(Connection conn, int studentId, int courseId) throws SQLException {
            String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, studentId);
                pstmt.setInt(2, courseId);
                pstmt.executeUpdate();
            }
        }

        // addBatch() and executeBatch() Demonstration
        public void batchInsertStudents(List<Student> students) throws SQLException {
            String sql = "INSERT INTO students (name, email, phone) VALUES (?, ?, ?)";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (Student s : students) {
                    pstmt.setString(1, s.getName());
                    pstmt.setString(2, s.getEmail());
                    pstmt.setString(3, s.getPhone());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
        }

        // CallableStatement Demonstration
        public List<Course> getStudentCoursesCallable(int studentId) throws SQLException {
            List<Course> courses = new ArrayList<>();
            String sql = "{CALL GetStudentCourses(?)}";
            try (Connection conn = DBConnection.getConnection();
                 CallableStatement cstmt = conn.prepareCall(sql)) {
                cstmt.setInt(1, studentId);
                try (ResultSet rs = cstmt.executeQuery()) {
                    while (rs.next()) {
                        courses.add(new Course(
                                rs.getInt("course_id"),
                                rs.getString("course_name"),
                                rs.getInt("credits")
                        ));
                    }
                }
            }
            return courses;
        }

        // SQL JOIN Report: Student Names with Course Names
        public List<String> getStudentCourseReport() throws SQLException {
            List<String> report = new ArrayList<>();
            String sql = "SELECT s.student_id, s.name AS student_name, c.course_id, c.course_name " +
                    "FROM students s " +
                    "JOIN enrollments e ON s.student_id = e.student_id " +
                    "JOIN courses c ON e.course_id = c.course_id " +
                    "ORDER BY s.name, c.course_name";
            try (Connection conn = DBConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String row = String.format("Student ID: %-3d | Name: %-15s | Course ID: %-3d | Course: %-20s",
                            rs.getInt("student_id"),
                            rs.getString("student_name"),
                            rs.getInt("course_id"),
                            rs.getString("course_name"));
                    report.add(row);
                }
            }
            return report;
        }

        public List<Student> getAllStudents() throws SQLException {
            List<Student> list = new ArrayList<>();
            String sql = "SELECT * FROM students";
            try (Connection conn = DBConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    list.add(new Student(rs.getInt("student_id"), rs.getString("name"), rs.getString("email"), rs.getString("phone")));
                }
            }
            return list;
        }

        public List<Course> getAllCourses() throws SQLException {
            List<Course> list = new ArrayList<>();
            String sql = "SELECT * FROM courses";
            try (Connection conn = DBConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    list.add(new Course(rs.getInt("course_id"), rs.getString("course_name"), rs.getInt("credits")));
                }
            }
            return list;
        }

}
