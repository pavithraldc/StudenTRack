package com.pavithra.demo.controller;

import com.pavithra.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("/calculate-age")
    public int calculateAge(@RequestParam("birth-year") int birthYear) {

        UserService userService = new UserService();

        int age = userService.calculateAge(birthYear);

        return age;
    }

    public String createUser(String userDetails) {
        //Save this user details in the DB

        return null;
    }
}
