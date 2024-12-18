package com.example.logggin;

public class ReadWriteUserDetails {
    public String fullName,dob,gender,mobile;
    public ReadWriteUserDetails(){

    }
    public ReadWriteUserDetails(String textDOB,String textGender,String textMobile)
    {

        this.dob=textDOB;
        this.gender=textGender;
        this.mobile=textMobile;

    }
    public ReadWriteUserDetails(String fullName, String dob, String gender, String mobile) {
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.mobile = mobile;
    }

}
