package com.example.logggin;

import java.util.ArrayList;
import java.util.List;

public class DoctorRepository {

    public static List<Doctor> getDoctorsByCategory(String category) {
        List<Doctor> doctors = new ArrayList<>();

        // Placeholder implementation
        switch (category) {
            case "Medicine Specialist":
                doctors.add(new Doctor("Dr. John Doe", "Medicine Specialist"));
                doctors.add(new Doctor("Dr. Jane Smith", "Medicine Specialist"));
                break;
            case "Child Specialist":
                doctors.add(new Doctor("Dr. Emily Johnson", "Child Specialist"));
                doctors.add(new Doctor("Dr. Michael Brown", "Child Specialist"));
                break;
            case "Neurologist":
                doctors.add(new Doctor("Dr. William Davis", "Neurologist"));
                doctors.add(new Doctor("Dr. Linda Martinez", "Neurologist"));
                break;
            case "Cardiologist":
                doctors.add(new Doctor("Dr. Robert Garcia", "Cardiologist"));
                doctors.add(new Doctor("Dr. Patricia Wilson", "Cardiologist"));
                break;
            case "Gynecologist":
                doctors.add(new Doctor("Dr. Barbara Anderson", "Gynecologist"));
                doctors.add(new Doctor("Dr. James Thomas", "Gynecologist"));
                break;
            case "Dermatologist":
                doctors.add(new Doctor("Dr. Christopher Jackson", "Dermatologist"));
                doctors.add(new Doctor("Dr. Mary White", "Dermatologist"));
                break;
            case "Gastroenterologist":
                doctors.add(new Doctor("Dr. Richard Harris", "Gastroenterologist"));
                doctors.add(new Doctor("Dr. Susan Clark", "Gastroenterologist"));
                break;
            case "Psychiatrist":
                doctors.add(new Doctor("Dr. Joseph Lewis", "Psychiatrist"));
                doctors.add(new Doctor("Dr. Karen Robinson", "Psychiatrist"));
                break;
            case "Orthopedic":
                doctors.add(new Doctor("Dr. Charles Walker", "Orthopedic"));
                doctors.add(new Doctor("Dr. Lisa Young", "Orthopedic"));
                break;
            case "ENT Specialist":
                doctors.add(new Doctor("Dr. Daniel Hall", "ENT Specialist"));
                doctors.add(new Doctor("Dr. Nancy Allen", "ENT Specialist"));
                break;
            default:
                break;
        }

        return doctors;
    }
}