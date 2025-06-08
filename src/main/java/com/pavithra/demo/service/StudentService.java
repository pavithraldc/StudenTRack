package com.pavithra.demo.service;

import com.pavithra.demo.entity.Students;
import com.pavithra.demo.model.CityCountDto;
import com.pavithra.demo.model.StudentDto;
import com.pavithra.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class StudentService {

    @Value("${sqlite.db.url}")
    private String url;

    @Autowired
    private StudentRepository studentRepository;

    public String getStudentName(int id) {

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to SQLite database.");

                // Create a statement
                Statement stmt = conn.createStatement();

                // Execute a simple query
                ResultSet rs = stmt.executeQuery("SELECT fname, lname FROM students WHERE id=" + id + ";");

                // Print table names
                while (rs.next()) {
                    return rs.getString("fname") + " " + rs.getString("lname");
                }
            }
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return "There is an exception in getting student name";
        }

        return "No Such Student";
    }

    public List<String> getStudentDataByCity(String city) {

        List<String> studentNames = new ArrayList<>();

        String sql = "SELECT fname FROM students WHERE city = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn != null) {
                System.out.println("Connected to SQLite database.");

                // Set the city parameter
                pstmt.setString(1, city);

                // Execute query
                ResultSet rs = pstmt.executeQuery();

                // Collect student names
                while (rs.next()) {
                    String studentName = rs.getString("fname");
                    studentNames.add(studentName);
                }
            }

        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return Collections.singletonList("There is an exception in getting student name");
        }

        return studentNames;
    }
    public List<CityCountDto> getStudentCountByCity() {
        String sql = "SELECT city, COUNT(*) AS student_count FROM students GROUP BY city;";

        List<CityCountDto> cityCountDtoList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String city = rs.getString("city");
                int count = rs.getInt("student_count");

                CityCountDto cityCountDto=new CityCountDto();
                cityCountDto.setCity(city);
                cityCountDto.setCount(count);
                cityCountDtoList.add(cityCountDto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cityCountDtoList;
    }



    public boolean deleteStudentById(int id) {

        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to SQLite database.");

                // Create a statement
                Statement stmt = conn.createStatement();

                // Execute a simple query
                int numberOfRowDeleted = stmt.executeUpdate("DELETE FROM students WHERE id = " + id + ";");
                if (numberOfRowDeleted > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getAllStudentsName() {

        List<String> allStudentsName = new ArrayList<>();


        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to SQLite database.");

                // Create a statement
                Statement stmt = conn.createStatement();

                // Execute a simple query
                ResultSet rs = stmt.executeQuery("SELECT FNAME,LNAME FROM STUDENTS");

                // Print table names
                while (rs.next()) {
                    String studentName = rs.getString("fname") + " " + rs.getString("lname");
                    allStudentsName.add(studentName);
                }
            }
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }

        return allStudentsName;
    }

    public List<StudentDto> getAllStudents() {

        List<StudentDto> allStudentDetailListDto = new ArrayList<>();

        List<Students> studentsList = studentRepository.findAll();

        for (Students student : studentsList) {
            StudentDto studentDto=new StudentDto();
            studentDto.setId(student.getId());
            studentDto.setFirstName(student.getFirstname());
            studentDto.setLastName(student.getLastName());
            studentDto.setDob(student.getDob());
            studentDto.setAddr1(student.getAddr1());
            studentDto.setAddr2(student.getAddr2());
            studentDto.setAddr3(student.getAddr3());
            studentDto.setCity(student.getCity());
            studentDto.setZip(student.getZip());
            studentDto.setMobile(student.getMobile());
            studentDto.setEmail(student.getEmail());

            allStudentDetailListDto.add(studentDto);




        }


        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to SQLite database.");

                // Create a statement
                Statement stmt = conn.createStatement();

                // Execute a simple query
                ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENTS");

                // Print table names
                while (rs.next()) {
                    StudentDto studentDto = new StudentDto();
                    studentDto.setId(rs.getInt("id"));
                    studentDto.setFirstName(rs.getString("fname"));
                    studentDto.setLastName(rs.getString("lname"));
                    studentDto.setDob(rs.getString("dob"));
                    studentDto.setAddr1(rs.getString("address1"));
                    studentDto.setAddr2(rs.getString("address2"));
                    studentDto.setAddr3(rs.getString("address3"));
                    studentDto.setCity(rs.getString("city"));
                    studentDto.setZip(rs.getInt("zipcode"));
                    studentDto.setMobile(rs.getLong("mobile"));
                    studentDto.setEmail(rs.getString("email"));

                    allStudentDetailListDto.add(studentDto);
                }
            }
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }

        return allStudentDetailListDto;
    }

    public List<StudentDto> getAllStudentsByFirstName(String fnStarting) {

        List<StudentDto> allStudentDetailListDto = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to SQLite database.");

                // Create a statement
                Statement stmt = conn.createStatement();

                // Execute a simple query
                ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENTS where fname like '" + fnStarting + "%'");

                // Print table names
                while (rs.next()) {
                    StudentDto studentDto = new StudentDto();
                    studentDto.setId(rs.getInt("id"));
                    studentDto.setFirstName(rs.getString("fname"));
                    studentDto.setLastName(rs.getString("lname"));
                    studentDto.setDob(rs.getString("dob"));
                    studentDto.setAddr1(rs.getString("address1"));
                    studentDto.setAddr2(rs.getString("address2"));
                    studentDto.setAddr3(rs.getString("address3"));
                    studentDto.setCity(rs.getString("city"));
                    studentDto.setZip(rs.getInt("zipcode"));
                    studentDto.setMobile(rs.getLong("mobile"));
                    studentDto.setEmail(rs.getString("email"));

                    allStudentDetailListDto.add(studentDto);
                }
            }
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }

        return allStudentDetailListDto;
    }
    public List<StudentDto> getAllStudentsByLastName(String lnStarting) {

        List<StudentDto> allStudentDetailListDto = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to SQLite database.");

                // Create a statement
                Statement stmt = conn.createStatement();

                // Execute a simple query
                ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENTS where lname like '" + lnStarting + "%'");

                // Print table names
                while (rs.next()) {
                    StudentDto studentDto = new StudentDto();
                    studentDto.setId(rs.getInt("id"));
                    studentDto.setFirstName(rs.getString("fname"));
                    studentDto.setLastName(rs.getString("lname"));
                    studentDto.setDob(rs.getString("dob"));
                    studentDto.setAddr1(rs.getString("address1"));
                    studentDto.setAddr2(rs.getString("address2"));
                    studentDto.setAddr3(rs.getString("address3"));
                    studentDto.setCity(rs.getString("city"));
                    studentDto.setZip(rs.getInt("zipcode"));
                    studentDto.setMobile(rs.getLong("mobile"));
                    studentDto.setEmail(rs.getString("email"));

                    allStudentDetailListDto.add(studentDto);
                }
            }
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }

        return allStudentDetailListDto;
    }


    public boolean updateStudentInfo(StudentDto studentDTO) {

        boolean validatedResult = validateStudentDtoinfo(studentDTO);

        if (!validatedResult) {
            System.out.println("Student information is invalid.Exiting");
            return false;
        }

        String sql = "UPDATE students SET fname=?, lname=?, dob=?, address1=?, address2=?, address3=?, city=?, zipcode=?, mobile=?, email=? WHERE id=?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, studentDTO.getFirstName());
            pstmt.setString(2, studentDTO.getLastName());
            pstmt.setString(3, studentDTO.getDob());
            pstmt.setString(4, studentDTO.getAddr1());
            pstmt.setString(5, studentDTO.getAddr2());
            pstmt.setString(6, studentDTO.getAddr3());
            pstmt.setString(7, studentDTO.getCity());
            pstmt.setInt(8, studentDTO.getZip());
            pstmt.setLong(9, studentDTO.getMobile());
            pstmt.setString(10, studentDTO.getEmail());
            pstmt.setInt(11, studentDTO.getId());

            int affectedRows = pstmt.executeUpdate();
            return (affectedRows > 0); // Returns true if update is successful

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This function creates the given student details in DB
     *
     * @param studentDto {@link StudentDto}
     * @return True if success, False Otherwise
     */
    public boolean insertStudentInDB(StudentDto studentDto) {
        boolean validatedResult = validateStudentDtoinfo(studentDto);

        if (!validatedResult) {
            System.out.println("Student information is invalid.Exiting");
            return false;
        }
        System.out.println(validatedResult);

        String sql = "INSERT INTO students (fname, lname, dob, address1, address2, address3, city, zipcode, mobile, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, studentDto.getFirstName());
            pstmt.setString(2, studentDto.getLastName());
            pstmt.setString(3, studentDto.getDob());
            pstmt.setString(4, studentDto.getAddr1());
            pstmt.setString(5, studentDto.getAddr2());
            pstmt.setString(6, studentDto.getAddr3());
            pstmt.setString(7, studentDto.getCity());
            pstmt.setInt(8, studentDto.getZip());
            pstmt.setLong(9, studentDto.getMobile());
            pstmt.setString(10, studentDto.getEmail());

            pstmt.executeUpdate();
            System.out.println("Student inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Exception in insertStudentInDB - " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean validateStudentDtoinfo(StudentDto studentDto) {
        int age = 0;

        if (studentDto == null) {
            System.out.println("studentDto is null");
            return false;
        }

        if (studentDto.getFirstName() == null) {
            System.out.println(" Firstname is null for " + studentDto.getLastName());
            return false;
        }
        if (studentDto.getLastName() == null) {
            System.out.println(studentDto.getFirstName() + " Lastname is null");
            return false;
        }

        if (studentDto.getDob() != null) {
            age = calculateAge(studentDto.getDob());
        }
        if (age > 100) {
            System.out.println(studentDto.getFirstName() + " age is " + age + " which is greater than 100. Exiting");
            return false;
        }
        if (age < 18) {
            System.out.println(studentDto.getFirstName() + " age is " + age + " which is lesser than 18. Exiting");
            return false;
        }

        if (studentDto.getCity() == null) {
            System.out.println(studentDto.getFirstName() + " city name is null");
            return false;
        }
        if (studentDto.getZip() == null) {
            System.out.println("Zip is null");
            return false;
        }


        return true;
    }

    public static int calculateAge(String dobString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try {
            LocalDate dob = LocalDate.parse(dobString, formatter);
            LocalDate currentDate = LocalDate.now();
            if (dob.isAfter(currentDate)) {
                throw new IllegalArgumentException("Date of birth cannot be in the future.");
            }
            return Period.between(dob, currentDate).getYears();
        } catch (Exception e) {
            System.out.println("Invalid date format or value: " + e.getMessage());
            return -1; // Or throw an exception depending on your use case
        }
    }

    public int countStudentsByCity(String city) {

        int count = 0;
        List<StudentDto> allStudents = getAllStudents();

        for (StudentDto studentDto : allStudents) {
            if (studentDto.getCity() != null && studentDto.getCity().equalsIgnoreCase(city)) {
                count = count + 1;
            }
        }
        return count;
    }

    public int countTeenagers() {

        int teenagersCount = 0;

        List<StudentDto> allStudents = getAllStudents();

        for (StudentDto studentDto : allStudents) {

            int age = calculateAge(studentDto.getDob());
            if (age >= 13 && age <= 19) {
                teenagersCount++;
            }

        }
        return teenagersCount;
    }

    }




