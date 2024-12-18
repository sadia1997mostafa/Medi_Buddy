// RegisterActivity.java
package com.example.logggin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsCompat.Type;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextRegisterFullName, editTextRegisterEmail, editTextRegisterDOB, editTextRegisterMobile, editTextRegisterPwd, editTextRegisterConfirmPwd;
    private RadioGroup radioGroupRegisterGender;
    private RadioButton radioButtonRegisterGenderSelected;
    private ProgressBar progressBar;
    private DatePickerDialog picker;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextRegisterFullName = findViewById(R.id.edit_text_register_full_name);
        editTextRegisterEmail = findViewById(R.id.edit_text_register_email);
        editTextRegisterDOB = findViewById(R.id.edit_text_register_dob);
        editTextRegisterMobile = findViewById(R.id.edit_text_register_mobile);
        editTextRegisterPwd = findViewById(R.id.edit_text_register_password);
        editTextRegisterConfirmPwd = findViewById(R.id.edit_text_register_confirm_password);
        radioGroupRegisterGender = findViewById(R.id.radio_group_register_gender);
        progressBar = findViewById(R.id.progressbar);

        editTextRegisterDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                picker = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextRegisterDOB.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        Button buttonRegister = findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedGenderId = radioGroupRegisterGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectedGenderId);

                String textFullName = editTextRegisterFullName.getText().toString();
                String textEmail = editTextRegisterEmail.getText().toString();
                String textDOB = editTextRegisterDOB.getText().toString();
                String textMobile = editTextRegisterMobile.getText().toString();
                String textPwd = editTextRegisterPwd.getText().toString();
                String textConfirmPwd = editTextRegisterConfirmPwd.getText().toString();
                String textGender;

                // Mobile number validation
                String mobileRegex = "^(?:\\+88)?01[3-9]\\d{8}$";
                Matcher mobileMatcher;
                Pattern mobilePattern = Pattern.compile(mobileRegex);
                mobileMatcher = mobilePattern.matcher(textMobile);

                if (TextUtils.isEmpty(textFullName)) {
                    Toast.makeText(RegisterActivity.this, "Please enter your full name", Toast.LENGTH_LONG).show();
                    editTextRegisterFullName.setError("Full name is required");
                    editTextRegisterFullName.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(RegisterActivity.this, "Please enter your Email", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Email is required");
                    editTextRegisterEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(RegisterActivity.this, "Please re-enter your Email", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Valid Email is required");
                    editTextRegisterEmail.requestFocus();
                } else if (TextUtils.isEmpty(textDOB)) {
                    Toast.makeText(RegisterActivity.this, "Please enter your date of birth", Toast.LENGTH_LONG).show();
                    editTextRegisterDOB.setError("Date of birth required");
                    editTextRegisterDOB.requestFocus();
                } else if (radioGroupRegisterGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(RegisterActivity.this, "Please select your gender", Toast.LENGTH_LONG).show();
                    radioButtonRegisterGenderSelected.setError("Gender selection is required");
                    radioButtonRegisterGenderSelected.requestFocus();
                } else if (TextUtils.isEmpty(textMobile)) {
                    Toast.makeText(RegisterActivity.this, "Please enter your mobile number", Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.setError("Mobile number is required");
                    editTextRegisterMobile.requestFocus();
                } else if (textMobile.length() != 11) {
                    Toast.makeText(RegisterActivity.this, "Please re-enter your mobile number", Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.setError("Mobile number should be of 11 digits");
                    editTextRegisterMobile.requestFocus();
                } else if (!mobileMatcher.find()) {
                    Toast.makeText(RegisterActivity.this, "Please re-enter your mobile number", Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.setError("Mobile is not valid");
                    editTextRegisterMobile.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(RegisterActivity.this, "Please enter your password", Toast.LENGTH_LONG).show();
                    editTextRegisterPwd.setError("Password is required");
                    editTextRegisterPwd.requestFocus();
                } else if (textPwd.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password should be at least 6 digits", Toast.LENGTH_LONG).show();
                    editTextRegisterPwd.setError("Password is too weak");
                    editTextRegisterPwd.requestFocus();
                } else if (TextUtils.isEmpty(textConfirmPwd)) {
                    Toast.makeText(RegisterActivity.this, "Please confirm your password", Toast.LENGTH_LONG).show();
                    editTextRegisterConfirmPwd.setError("Password confirmation is required");
                    editTextRegisterConfirmPwd.requestFocus();
                } else if (!textPwd.equals(textConfirmPwd)) {
                    Toast.makeText(RegisterActivity.this, "Please enter the same password you entered in the password option", Toast.LENGTH_LONG).show();
                    editTextRegisterConfirmPwd.setError("This password should be matched with the previously given one");
                    editTextRegisterConfirmPwd.requestFocus();
                    editTextRegisterPwd.clearComposingText();
                    editTextRegisterConfirmPwd.clearComposingText();
                } else {
                    textGender = radioButtonRegisterGenderSelected.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textFullName, textEmail, textDOB, textGender, textMobile, textPwd);
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void registerUser(String textFullName, String textEmail, String textDOB, String textGender, String textMobile, String textPwd) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail, textPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullName).build();
                    firebaseUser.updateProfile(profileChangeRequest);
                    // Enter user data into Realtime Database
                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textDOB, textGender, textMobile);

                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                firebaseUser.sendEmailVerification();
                                Toast.makeText(RegisterActivity.this, "User registration completed successfully. Please verify your email", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(RegisterActivity.this, UserProfileActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "User registration failed. Please verify your email", Toast.LENGTH_LONG).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        editTextRegisterPwd.setError("Your password is too weak");
                        editTextRegisterPwd.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        editTextRegisterEmail.setError("Your email is invalid or already registered");
                        editTextRegisterEmail.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e) {
                        editTextRegisterEmail.setError("User is already registered with this Email. Use another Email");
                        editTextRegisterEmail.requestFocus();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}