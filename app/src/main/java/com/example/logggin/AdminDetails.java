package com.example.logggin;

public class AdminDetails {
    public String dob;
    public String gender;
    public String mobile;
    public String email;

    public AdminDetails() {
        // Default constructor required for calls to DataSnapshot.getValue(AdminDetails.class)
    }

    public AdminDetails(String dob, String gender, String mobile, String email) {
        this.dob = dob;
        this.gender = gender;
        this.mobile = mobile;
        this.email = email;
    }
}