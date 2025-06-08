package com.pavithra.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Students {

    @Id
    private int id;
    private String fname;
    private String lname;
    private String dob;
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private int zipcode;
    private long mobile;
    private String email;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getFirstname() {
        return fname;
    }

    public void setFirstName(String firstName) {
        this.fname = firstName;
    }

    public String getLastName() {
        return lname;
    }

    public void setLastName(String lastName) {
        this.lname = lastName;
    }

    public String getFullName(){
        return this.fname + " " + this.lname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddr1() {
        return address1;
    }

    public void setAddr1(String addr1) {
        this.address1 = addr1;
    }

    public String getAddr2() {
        return address2;
    }

    public void setAddr2(String addr2) {
        this.address2 = addr2;
    }

    public String getAddr3() {
        return address3;
    }

    public void setAddr3(String addr3) {
        this.address3 = addr3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getZip() {
        return zipcode;
    }

    public void setZip(int zip) {
        this.zipcode = zip;
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


