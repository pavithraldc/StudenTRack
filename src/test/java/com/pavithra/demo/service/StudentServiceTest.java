package com.pavithra.demo.service;

import com.pavithra.demo.model.StudentDto;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class StudentServiceTest {

    private StudentService studentService = new StudentService();

    @Test
    public void test_validateStudentDtoinfo_pass_new_object() {

        StudentDto studentDto = new StudentDto();
        boolean isValid = studentService.validateStudentDtoinfo(studentDto);
        Assert.isTrue(!isValid, "Empty studentDto should be invalid");
    }

    @Test
    public void test_validateStudentDtoinfo_pass_null() {

        StudentDto studentDto = null;
        boolean isValid = studentService.validateStudentDtoinfo(studentDto);
        Assert.isTrue(!isValid, "Empty studentDto should be invalid");
    }
}
