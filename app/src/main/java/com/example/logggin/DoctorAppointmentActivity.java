package com.example.logggin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class DoctorAppointmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment);

        CardView medicineSpecialistCard = findViewById(R.id.cardFDMedicineSpecialist);
        medicineSpecialistCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorAppointmentActivity.this, FindDoctorActivity.class);
                intent.putExtra("category", "Medicine Specialist");
                startActivity(intent);
            }
        });

        CardView childSpecialistCard = findViewById(R.id.cardFDChildSpecialist);
        childSpecialistCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorAppointmentActivity.this, FindDoctorActivity.class);
                intent.putExtra("category", "Child Specialist");
                startActivity(intent);
            }
        });

        CardView neurologistCard = findViewById(R.id.cardFDNeurologist);
        neurologistCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorAppointmentActivity.this, FindDoctorActivity.class);
                intent.putExtra("category", "Neurologist");
                startActivity(intent);
            }
        });

        CardView cardiologistCard = findViewById(R.id.cardFDCardiologist);
        cardiologistCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorAppointmentActivity.this, FindDoctorActivity.class);
                intent.putExtra("category", "Cardiologist");
                startActivity(intent);
            }
        });

        CardView gynecologistCard = findViewById(R.id.cardFDGynecologist);
        gynecologistCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorAppointmentActivity.this, FindDoctorActivity.class);
                intent.putExtra("category", "Gynecologist");
                startActivity(intent);
            }
        });

        CardView dermatologistCard = findViewById(R.id.cardFDDermatologist);
        dermatologistCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorAppointmentActivity.this, FindDoctorActivity.class);
                intent.putExtra("category", "Dermatologist");
                startActivity(intent);
            }
        });

        CardView gastroenterologistCard = findViewById(R.id.cardFDGastroenterologist);
        gastroenterologistCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorAppointmentActivity.this, FindDoctorActivity.class);
                intent.putExtra("category", "Gastroenterologist");
                startActivity(intent);
            }
        });

        CardView orthopedistCard = findViewById(R.id.cardFDOrthopedist);
        orthopedistCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorAppointmentActivity.this, FindDoctorActivity.class);
                intent.putExtra("category", "Orthopedist");
                startActivity(intent);
            }
        });

        CardView psychiatristCard = findViewById(R.id.cardFDPsychiatrist);
        psychiatristCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorAppointmentActivity.this, FindDoctorActivity.class);
                intent.putExtra("category", "Psychiatrist");
                startActivity(intent);
            }
        });

        CardView dentistCard = findViewById(R.id.cardFDDentist);
        dentistCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorAppointmentActivity.this, FindDoctorActivity.class);
                intent.putExtra("category", "Dentist");
                startActivity(intent);
            }
        });
    }
}