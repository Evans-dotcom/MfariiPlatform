package com.EcharityPlatform.MfarijiPlatform.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegistrationBody {

    @Size(min=3, max=255)
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private int nationalID;
    private int  phoneNo;
    @Email
    private String email;
    private int age;
    private String county;
    private String constituency;
    private String ward;
    private String location;
    private String village;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
    @Size(min=6, max=32)
    private String password;


    public RegistrationBody(String username,
                            String firstName,
                            String middleName,
                            String lastName,
                            int nationalID,
                            int phoneNo,
                            String email,
                            int age,
                            String county,
                            String constituency,
                            String ward,
                            String location,
                            String village,
                            String password) {
        this.username = username;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.nationalID = nationalID;
        this.phoneNo = phoneNo;
        this.email = email;
        this.age = age;
        this.county = county;
        this.constituency = constituency;
        this.ward = ward;
        this.location= location;
        this.village = village;
        this.password = password;
    }


    public String getUserName() {
        return username;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getNationalID() {
        return nationalID;
    }

    public int getPhoneNo() {
        return phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getCounty() {
        return county;
    }

    public String getConstituency() {
        return constituency;
    }

    public String getWard() {
        return ward;
    }
    public String getLocation() {
        return location;
    }

    public String getVillage() {
        return village;
    }

    public String getPassword() {
        return password;
    }
}
