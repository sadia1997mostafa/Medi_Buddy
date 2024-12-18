package com.example.logggin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddHospitalActivity extends AppCompatActivity {

    private EditText hospitalName, hospitalAddress, hospitalPhone;
    private Button addHospitalButton;
    private DatabaseReference databaseHospitals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hospital);

        hospitalName = findViewById(R.id.hospital_name);
        hospitalAddress = findViewById(R.id.hospital_address);
        hospitalPhone = findViewById(R.id.hospital_phone);
        addHospitalButton = findViewById(R.id.add_hospital_button);

        databaseHospitals = FirebaseDatabase.getInstance().getReference("hospitals");

        addHospitalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHospital();
            }
        });
    }

    private void addHospital() {
        String name = hospitalName.getText().toString().trim();
        String address = hospitalAddress.getText().toString().trim();
        String phone = hospitalPhone.getText().toString().trim();

        if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = databaseHospitals.push().getKey();
        Hospital hospital = new Hospital(id, name, address, phone);
        databaseHospitals.child(id).setValue(hospital);

        Toast.makeText(this, "Hospital added", Toast.LENGTH_SHORT).show();
        finish();
    }
}