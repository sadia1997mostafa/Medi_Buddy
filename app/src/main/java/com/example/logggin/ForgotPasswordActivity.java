package com.example.logggin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class ForgotPasswordActivity extends AppCompatActivity {
  private Button buttonResetPwd;
  private EditText editTextResetEmail;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;
    private static final String TAG="ForgotPasswordActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Forgot Password");

        }
        editTextResetEmail=findViewById(R.id.editText_forgot_password_reset_email);
        progressBar=findViewById(R.id.progressbar);

        buttonResetPwd=findViewById(R.id.button_forgot_password_reset);
        buttonResetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=editTextResetEmail.getText().toString();
                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter your registered email.", Toast.LENGTH_SHORT).show();
                    editTextResetEmail.setError("Email is required.");
                    editTextResetEmail.requestFocus();

                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                    editTextResetEmail.setError("Valid email is required.");
                    editTextResetEmail.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    resetPassword(email);

                }
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void resetPassword(String email) {
        authProfile=FirebaseAuth.getInstance();
        authProfile.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ForgotPasswordActivity.this, "Password reset link has been sent to your email. Please check your inbox.", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ForgotPasswordActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else {
                    try{
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        editTextResetEmail.setError("Email is not registered or is no longer valid. Please register again.");

                    }catch (Exception e)
                    {
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(ForgotPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                    progressBar.setVisibility(View.GONE);

                }
            }
        });
    }
}