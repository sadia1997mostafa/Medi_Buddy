package com.example.logggin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AdminMainActivity extends AppCompatActivity {

    private CardView addDoctorCard, addHospitalCard,orderRequestsCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        addDoctorCard = findViewById(R.id.add_doctor_card);
        addHospitalCard = findViewById(R.id.add_hospital_card);
        orderRequestsCard=findViewById(R.id.order_requests_card);

        addDoctorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, AddDoctorActivity.class);
                startActivity(intent);
            }
        });

        addHospitalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, AddHospitalActivity.class);
                startActivity(intent);
            }
        });
        View manageMedicineCard = findViewById(R.id.manage_medicine_card);
        manageMedicineCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, ManageMedicineActivity.class);
                startActivity(intent);
            }
        });
        orderRequestsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this,OrderRequestActivity .class);
                startActivity(intent);
            }
        });
    }
}