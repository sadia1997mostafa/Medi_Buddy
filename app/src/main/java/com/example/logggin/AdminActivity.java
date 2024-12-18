// AdminActivity.java
package com.example.logggin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {

    private EditText editTextAdminDOB, editTextAdminGender, editTextAdminMobile, editTextAdminEmail;
    private Button buttonStoreAdminDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        editTextAdminDOB = findViewById(R.id.editText_admin_dob);
        editTextAdminGender = findViewById(R.id.editText_admin_gender);
        editTextAdminMobile = findViewById(R.id.editText_admin_mobile);
        editTextAdminEmail = findViewById(R.id.editText_admin_email);
        buttonStoreAdminDetails = findViewById(R.id.button_store_admin_details);

        buttonStoreAdminDetails.setOnClickListener(v -> storeAdminDetails());
    }

    private void storeAdminDetails() {
        String dob = editTextAdminDOB.getText().toString();
        String gender = editTextAdminGender.getText().toString();
        String mobile = editTextAdminMobile.getText().toString();
        String email = editTextAdminEmail.getText().toString();

        if (dob.isEmpty() || gender.isEmpty() || mobile.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference adminRef = database.getReference("adminUID").child("VVfwYU7b2AWgHgWGF4lpD4jcCUC2");

        AdminDetails adminDetails = new AdminDetails(dob, gender, mobile, email);
        adminRef.setValue(adminDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Admin details stored successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to store admin details", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}