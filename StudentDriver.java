package com.StudentCourseSystem;

import java.util.List;
import java.util.Scanner;

public class StudentDriver {
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            RegistrationService service = new RegistrationService();

            while (true) {
                System.out.println("==========================================");
                System.out.println("|| STUDENT COURSE REGISTRATION SYSTEM   ||");
                System.out.println("==========================================");
                System.out.println("1. Register New Student");
                System.out.println("2. Add New Course");
                System.out.println("3. Enroll Student in Course (Transaction)");
                System.out.println("4. View All Students");
                System.out.println("5. View All Courses");
                System.out.println("6. View Courses Taken by Student (CallableStatement)");
                System.out.println("7. View Student-Course Report (SQL JOIN)");
                System.out.println("0. Exit");

                System.out.print("Enter your choice: ");
                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    sc.next();
                }
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter Student Name: ");
                        String sName = sc.nextLine();
                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();
                        System.out.print("Enter Phone: ");
                        String phone = sc.nextLine();
                        service.registerStudent(new Student(sName, email, phone));
                        break;

                    case 2:
                        System.out.print("Enter Course Name: ");
                        String cName = sc.nextLine();
                        System.out.print("Enter Credits: ");
                        int credits = sc.nextInt();
                        service.addCourse(new Course(cName, credits));
                        break;

                    case 3:
                        System.out.print("Enter Student ID: ");
                        int sId = sc.nextInt();
                        System.out.print("Enter Course ID: ");
                        int cId = sc.nextInt();
                        service.enrollStudentWithTransaction(sId, cId);
                        break;

                    case 4:
                        List<Student> students = service.getAllStudents();
                        System.out.println("\n--- Registered Students ---");
                        if (students != null) {
                            for (Student s : students) {
                                System.out.printf("ID: %d | Name: %s | Email: %s | Phone: %s\n",
                                        s.getStudentId(), s.getName(), s.getEmail(), s.getPhone());
                            }
                        }
                        System.out.println();
                        break;

                    case 5:
                        List<Course> courses = service.getAllCourses();
                        System.out.println("\n--- Available Courses ---");
                        if (courses != null) {
                            for (Course c : courses) {
                                System.out.printf("ID: %d | Course: %s | Credits: %d\n",
                                        c.getCourseId(), c.getCourseName(), c.getCredits());
                            }
                        }
                        System.out.println();
                        break;

                    case 6:
                        System.out.print("Enter Student ID to check courses: ");
                        int searchId = sc.nextInt();
                        List<Course> enrolledCourses = service.getStudentCoursesCallable(searchId);
                        System.out.println("\n--- Courses for Student ID: " + searchId + " ---");
                        if (enrolledCourses != null && !enrolledCourses.isEmpty()) {
                            for (Course c : enrolledCourses) {
                                System.out.printf("Course ID: %d | Name: %s | Credits: %d\n",
                                        c.getCourseId(), c.getCourseName(), c.getCredits());
                            }
                        } else {
                            System.out.println("No courses found for this student.");
                        }
                        System.out.println();
                        break;

                    case 7:
                        List<String> report = service.getStudentCourseReport();
                        System.out.println("\n--- Student-Course Registration Report (SQL JOIN) ---");
                        if (report != null && !report.isEmpty()) {
                            for (String row : report) {
                                System.out.println(row);
                            }
                        } else {
                            System.out.println("No enrollment records found.");
                        }
                        System.out.println();
                        break;

                    case 0:
                        System.out.println("Exiting application. Goodbye!");
                        sc.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.\n");
                }
            }
        }

}
