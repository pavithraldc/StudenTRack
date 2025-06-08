package com.pavithra.demo.controller;

import com.pavithra.demo.model.CityCountDto;
import com.pavithra.demo.model.StudentDto;
import com.pavithra.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    /**
     * This function creates the given student details in DB
     * @param studentDtoObj {@link StudentDto}
     * @return success if created, failure otherwise
     */
    @PostMapping("/create")
    @CrossOrigin(origins = "*")
    public String createStudent(@RequestBody StudentDto studentDtoObj) {
        System.out.println("createUser for " + studentDtoObj.getFirstName());
        boolean isSuccess = studentService.insertStudentInDB(studentDtoObj);

        if (isSuccess) {
            return "success";
        } else {
            return "failure";
        }

    }

    @GetMapping("/read/{id}")
    @CrossOrigin(origins = "*") // Allow requests only from specific origin
    public String getStudentData(@PathVariable int id) {

        System.out.println("getStudentData - " + id);


        String name = studentService.getStudentName(id);
        return name ;
    }
    @GetMapping("/city/{city}")
    @CrossOrigin(origins = "*") // Allow requests only from specific origin
    public List<String> getStudentDataByCity(@PathVariable String city) {

        System.out.println("getStudentData - " + city);
        return Collections.singletonList(studentService.getStudentDataByCity(city).toString());
    }



    @GetMapping("/read")
    @CrossOrigin(origins = "*") // Allow requests only from specific origin
    public List<StudentDto> getAllStudents(@RequestParam(required = false) String fnStarting,String lnStarting) {

        System.out.println("Called Get All Students");


        List<StudentDto> allStudentsList;

        if (lnStarting != null) {
            allStudentsList = studentService.getAllStudentsByLastName(lnStarting);
        } else if (fnStarting != null){
            allStudentsList = studentService.getAllStudentsByFirstName(fnStarting);
        } else {
            allStudentsList = studentService.getAllStudents();

        }

        return allStudentsList ;
    }

    @PutMapping("/update")
    @CrossOrigin(origins = "*")
    public String updateStudent(@RequestBody StudentDto studentDto) {
        System.out.println("Updating student info for " + studentDto.getId());

        boolean isUpdateSuccess = studentService.updateStudentInfo(studentDto);
        if (isUpdateSuccess) {
            return "updated";
        } else {
            return "not updated";
        }
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        System.out.println("Deleting student info for " + id);

        boolean isDeleteSuccess = studentService.deleteStudentById(id);
        if (isDeleteSuccess) {
            return "Deleted";
        } else {
            return "not Deleted";
        }
    }


    @GetMapping("/count/city")
    public List<CityCountDto> getStudentCountByCity() {
        System.out.println("Fetching student count for each city");

        return studentService.getStudentCountByCity();
    }



    @GetMapping("/count")
    public int getStudentCountByCity(@RequestParam String city) {
        return studentService.countStudentsByCity(city);
    }

    // Allow cross-origin requests (e.g. from frontend)


    @GetMapping("/teenagers/count")
    @CrossOrigin(origins = "*") // Allow requests only from specific origin
    public int countTeenagers() {

        System.out.println("The Number of Teenagers");

        int countTeenagers = studentService.countTeenagers();
        return countTeenagers ;
    }

    @GetMapping("/senior-citizens/count")
    @CrossOrigin(origins = "*") // Allow requests only from specific origin
    public int countSeniorCitizens() {

        System.out.println("The Number of SeniorCitizens");

        int countSeniorCitizens = studentService.countSeniorCitizens();
        return countSeniorCitizens ;
    }

}
