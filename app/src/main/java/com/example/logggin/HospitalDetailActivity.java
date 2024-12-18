package com.example.logggin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HospitalDetailActivity extends AppCompatActivity {

    private TextView hospitalName;
    private TextView hospitalAddress;
    private TextView hospitalPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_detail);

        hospitalName = findViewById(R.id.hospital_name);
        hospitalAddress = findViewById(R.id.hospital_address);
        hospitalPhone = findViewById(R.id.hospital_phone);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String phone = intent.getStringExtra("phone");

        if (name != null) {
            hospitalName.setText(name);
        }
        if (address != null) {
            hospitalAddress.setText(address);
        }
        if (phone != null) {
            hospitalPhone.setText(phone);
        }
    }
}