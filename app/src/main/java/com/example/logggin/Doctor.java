package com.example.logggin;

import java.io.Serializable;

public class Doctor implements Serializable {
    private String id;
    private String name;
    private String address;
    private String qualifications;
    private String chamber;
    private String hospital;
    private String phone;
    private String category;
    private String imagePath;

    // Default constructor required for calls to DataSnapshot.getValue(Doctor.class)
    public Doctor() {
    }

    // Constructor with parameters
    public Doctor(String id, String name, String address, String qualifications, String chamber, String hospital, String phone, String category, String imagePath) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.qualifications = qualifications;
        this.chamber = chamber;
        this.hospital = hospital;
        this.phone = phone;
        this.category = category;
        this.imagePath = imagePath;
    }

    // New constructor with name and specialty
    public Doctor(String name, String specialty) {
        this.name = name;
        this.category = specialty;
    }

    // Getters and setters for each field
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}