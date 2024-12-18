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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText editTextCurrentPassword, editTextNewPassword, editTextConfirmNewPassword;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        editTextCurrentPassword = findViewById(R.id.edit_text_current_password);
        editTextNewPassword = findViewById(R.id.edit_text_new_password);
        editTextConfirmNewPassword = findViewById(R.id.edit_text_confirm_new_password);
        progressBar = findViewById(R.id.progress_bar);
        Button buttonChangePassword = findViewById(R.id.button_change_password);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        buttonChangePassword.setOnClickListener(v -> changePassword(firebaseUser));
    }

    private void changePassword(FirebaseUser firebaseUser) {
        String currentPassword = editTextCurrentPassword.getText().toString().trim();
        String newPassword = editTextNewPassword.getText().toString().trim();
        String confirmNewPassword = editTextConfirmNewPassword.getText().toString().trim();

        if (TextUtils.isEmpty(currentPassword)) {
            editTextCurrentPassword.setError("Current password is required");
            editTextCurrentPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(newPassword)) {
            editTextNewPassword.setError("New password is required");
            editTextNewPassword.requestFocus();
            return;
        }

        if (newPassword.length() < 6) {
            editTextNewPassword.setError("Password should be at least 6 characters");
            editTextNewPassword.requestFocus();
            return;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            editTextConfirmNewPassword.setError("Passwords do not match");
            editTextConfirmNewPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // Re-authenticate the user
        AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), currentPassword);
        firebaseUser.reauthenticate(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Update the password
                firebaseUser.updatePassword(newPassword).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Toast.makeText(ChangePasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "Failed to change password", Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                });
            } else {
                Toast.makeText(ChangePasswordActivity.this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}