package com.pavithra.demo.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

public class UserService {

    public int calculateAge(int birthYear) {
        int currentYear = LocalDate.now().getYear();
        return currentYear - birthYear;
    }
}
