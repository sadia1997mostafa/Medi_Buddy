package com.example.logggin;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class DoctorDetailsActivity extends AppCompatActivity {

    private TextView doctorName, doctorAddress, doctorQualifications, doctorChamber, doctorHospital, doctorPhone, doctorCategory;
    private ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        // Initialize TextViews
        doctorName = findViewById(R.id.doctor_name);
        doctorAddress = findViewById(R.id.doctor_address);
        doctorQualifications = findViewById(R.id.doctor_qualifications);
        doctorChamber = findViewById(R.id.doctor_chamber);
        doctorHospital = findViewById(R.id.doctor_hospital);
        doctorPhone = findViewById(R.id.doctor_phone);
        doctorCategory = findViewById(R.id.doctor_category);
        profilePicture = findViewById(R.id.profile_picture);

        // Get the Doctor object from the Intent
        Doctor doctor = (Doctor) getIntent().getSerializableExtra("doctor");

        // Set data to TextViews if the Doctor object is not null
        if (doctor != null) {
            Log.d("DoctorDetailsActivity", "Doctor data: " + doctor.toString());
            doctorName.setText(doctor.getName());
            doctorAddress.setText(doctor.getAddress());
            doctorQualifications.setText(doctor.getQualifications());
            doctorChamber.setText(doctor.getChamber());
            doctorHospital.setText(doctor.getHospital());
            doctorPhone.setText(doctor.getPhone());
            doctorCategory.setText(doctor.getCategory());

            // Load the profile picture using Glide
            Glide.with(this)
                    .load(doctor.getImagePath())
                    .into(profilePicture);
        } else {
            Log.e("DoctorDetailsActivity", "Doctor data is null");
        }
    }
}