package com.example.logggin;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText editTextFullName, editTextDOB, editTextGender, editTextMobile;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        editTextFullName = findViewById(R.id.edit_text_full_name);
        editTextDOB = findViewById(R.id.edit_text_dob);
        editTextGender = findViewById(R.id.edit_text_gender);
        editTextMobile = findViewById(R.id.edit_text_mobile);
        progressBar = findViewById(R.id.progress_bar);
        Button buttonUpdate = findViewById(R.id.button_update);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users").child(firebaseUser.getUid());

        buttonUpdate.setOnClickListener(v -> updateProfile());
    }

    private void updateProfile() {
        String fullName = editTextFullName.getText().toString().trim();
        String dob = editTextDOB.getText().toString().trim();
        String gender = editTextGender.getText().toString().trim();
        String mobile = editTextMobile.getText().toString().trim();

        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(dob) || TextUtils.isEmpty(gender) || TextUtils.isEmpty(mobile)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        ReadWriteUserDetails userDetails = new ReadWriteUserDetails(fullName, dob, gender, mobile);
        databaseReference.setValue(userDetails).addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                Toast.makeText(UpdateProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(UpdateProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
            }
        });
    }
}