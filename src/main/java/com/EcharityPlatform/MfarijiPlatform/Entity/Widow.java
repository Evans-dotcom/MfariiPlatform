package com.EcharityPlatform.MfarijiPlatform.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "widow_user")
public class Widow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username",nullable = false,unique = true)
    private String username;
    @Column(name = "first_name", nullable = false, length = 255)
    private String firstName;
    @Column(name = "middle_name", nullable = false,length = 255)
    private String middleName;
    @Column(name = "last_name", nullable = false,length = 255)
    private String lastName;
    @Column(name = "national_Id", nullable = false,length = 8)
    private int nationalID;
    @Column(name="phone_No", nullable = false, length = 12)
    private int phoneNo;
    @Column(name = "email", nullable = false,unique = true,length = 320)
    private String email;
    @Column(name = "age", nullable = false,length = 100)
    private int age;
    @Column(name = "county", nullable = false,length = 255)
    private String county;

    @Column(name = "constituency",length = 255)
    private String constituency;
    @Column(name = "ward", nullable = false,length = 255)
    private String ward;
    @Column(name = "location", nullable = false,length = 255)
    private String location;
    @Column(name = "village", nullable = false,length = 255)
    private String village;
    @Column(name = "password", nullable = false,length = 1000)
    private String password;


    @OneToMany(mappedBy = "widow", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id desc")
    private List<VerificationToken> verificationTokens = new ArrayList<>();
    /** Has the widows email been verified? */
    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified = false;

    /**
     * Is the email verified?
     * @return True if it is, false otherwise.
     */
    public Boolean isEmailVerified() {
        return emailVerified;
    }

    /**
     * Sets the email verified state.
     * @param emailVerified The verified state.
     */
    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    /**
     * Gets the list of VerificationTokens sent to the user.
     * @return The list.
     */
    public List<VerificationToken> getVerificationTokens() {
        return verificationTokens;
    }

    /**
     * Sets the list of VerificationTokens sent to the user.
     * @param verificationTokens The list.
     */
    public void setVerificationTokens(List<VerificationToken> verificationTokens) {
        this.verificationTokens = verificationTokens;
    }


    public String getFirstName() {
        return firstName;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getNationalID() {
        return nationalID;
    }

    public void setNationalID(int nationalID) {
        nationalID = nationalID;
    }

    public int getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(int phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        age = age;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        county = county;
    }

    public String getConstituency() {
        return constituency;
    }

    public void setConstituency(String constituency) {
        constituency = constituency;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        ward = ward;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        location = location;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        village = village;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Boolean getEmailVerified() {
        return emailVerified;
    }
}
