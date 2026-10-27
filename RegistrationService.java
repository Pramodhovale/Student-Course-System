package com.StudentCourseSystem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class RegistrationService {
        private final RegistrationDAO dao = new RegistrationDAO();

        // Transaction Management: commit() and rollback()
        public void enrollStudentWithTransaction(int studentId, int courseId) {
            try (Connection conn = DBConnection.getConnection()) {
                conn.setAutoCommit(false); // Begin transaction

                dao.enrollStudent(conn, studentId, courseId);

                conn.commit(); // Commit transaction
                System.out.println("Success: Student successfully enrolled in course.\n");
            } catch (SQLException e) {
                System.err.println("Enrollment Failed (Triggering Rollback): " + e.getMessage() + "\n");
            }
        }

        public void registerStudent(Student student) {
            try (Connection conn = DBConnection.getConnection()) {
                conn.setAutoCommit(false);
                dao.registerStudent(conn, student);
                conn.commit();
                System.out.println("Student registered successfully!\n");
            } catch (SQLException e) {
                System.out.println("Error registering student: " + e.getMessage() + "\n");
            }
        }

        public void addCourse(Course course) {
            try (Connection conn = DBConnection.getConnection()) {
                conn.setAutoCommit(false);
                dao.addCourse(conn, course);
                conn.commit();
                System.out.println("Course added successfully!\n");
            } catch (SQLException e) {
                System.out.println("Error adding course: " + e.getMessage() + "\n");
            }
        }

        public List<Student> getAllStudents() {
            try {
                return dao.getAllStudents();
            } catch (SQLException e) {
                return null;
            }
        }

        public List<Course> getAllCourses() {
            try {
                return dao.getAllCourses();
            } catch (SQLException e) {
                return null;
            }
        }

        public List<Course> getStudentCoursesCallable(int studentId) {
            try {
                return dao.getStudentCoursesCallable(studentId);
            } catch (SQLException e) {
                return null;
            }
        }

        public List<String> getStudentCourseReport() {
            try {
                return dao.getStudentCourseReport();
            } catch (SQLException e) {
                return null;
            }
        }
}
