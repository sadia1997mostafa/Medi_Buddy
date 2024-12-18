package com.example.logggin;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextLoginEmail, editTextLoginPwd;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;
    private static final String TAG = "LoginActivity";

    private static final String ADMIN_EMAIL = "nasimac300@gmail.com";
    private static final String ADMIN_PASSWORD = "admin123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Login");
        }
        editTextLoginEmail = findViewById(R.id.editText_login_email);
        editTextLoginPwd = findViewById(R.id.editText_login_pwd);
        progressBar = findViewById(R.id.progressbar);
        authProfile = FirebaseAuth.getInstance();

        Button buttonForgotPwd = findViewById(R.id.button_forgot_pwd);
        buttonForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "You can reset your password now.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

        ImageView imageViewShowHidePwd = findViewById(R.id.imageView_show_hide_pwd);
        imageViewShowHidePwd.setImageResource(R.drawable.ic_hide_pwd);
        imageViewShowHidePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextLoginPwd.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    editTextLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageViewShowHidePwd.setImageResource(R.drawable.ic_hide_pwd);
                } else {
                    editTextLoginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowHidePwd.setImageResource(R.drawable.ic_show_pwd);
                }
            }
        });

        Button btnLoginUser = findViewById(R.id.btnLoginUser);
        btnLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        Button btnLoginAdmin = findViewById(R.id.btnLoginAdmin);
        btnLoginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAdmin();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loginUser() {
        String email = editTextLoginEmail.getText().toString();
        String pwd = editTextLoginPwd.getText().toString();
        if (validateInputs(email, pwd)) {
            progressBar.setVisibility(View.VISIBLE);
            authProfile.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = authProfile.getCurrentUser();
                        if (firebaseUser != null && firebaseUser.isEmailVerified()) {
                            showSaveLoginDialog();
                        } else {
                            handleUnverifiedEmail();
                        }
                    } else {
                        handleLoginFailure(task);
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    private void loginAdmin() {
        String email = editTextLoginEmail.getText().toString();
        String pwd = editTextLoginPwd.getText().toString();
        if (validateInputs(email, pwd)) {
            if (email.equals(ADMIN_EMAIL) && pwd.equals(ADMIN_PASSWORD)) {
                progressBar.setVisibility(View.VISIBLE);
                authProfile.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = authProfile.getCurrentUser();
                            if (firebaseUser != null && firebaseUser.isEmailVerified()) {
                                Toast.makeText(LoginActivity.this, "You are logged in now", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                                finish();
                            } else {
                                handleUnverifiedEmail();
                            }
                        } else {
                            handleLoginFailure(task);
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
            } else {
                Toast.makeText(LoginActivity.this, "Invalid admin credentials", Toast.LENGTH_LONG).show();
            }
        }
    }
    private boolean validateInputs(String email, String pwd) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(LoginActivity.this, "Please enter your email", Toast.LENGTH_LONG).show();
            editTextLoginEmail.setError("Email is required");
            editTextLoginEmail.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(LoginActivity.this, "Please re-enter your email", Toast.LENGTH_LONG).show();
            editTextLoginEmail.setError("Valid Email is required");
            editTextLoginEmail.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_LONG).show();
            editTextLoginPwd.setError("Password is required");
            editTextLoginPwd.requestFocus();
            return false;
        }
        return true;
    }

    private void handleUnverifiedEmail() {
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification();
            authProfile.signOut();
            showAlertDialog();
        }
    }

    private void handleLoginFailure(@NonNull Task<AuthResult> task) {
        try {
            throw task.getException();
        } catch (FirebaseAuthInvalidUserException e) {
            editTextLoginEmail.setError("User does not exist or is no longer valid. Please register again");
            editTextLoginEmail.requestFocus();
        } catch (FirebaseAuthInvalidCredentialsException e) {
            editTextLoginEmail.setError("Invalid Credentials. Kindly check and re-enter");
            editTextLoginEmail.requestFocus();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Email not verified");
        builder.setMessage("Please verify your email. You cannot login without email verification");
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showSaveLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save Login Details");
        builder.setMessage("Do you want to save your login details?");
        builder.setPositiveButton("Yes", (dialog, which) -> saveLoginDetails(true));
        builder.setNegativeButton("No", (dialog, which) -> saveLoginDetails(false));
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveLoginDetails(boolean save) {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("saveLogin", save);
        editor.apply();

        Intent intent = new Intent(LoginActivity.this, save ? HomeActivity.class : LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authProfile.getCurrentUser() != null) {
            Toast.makeText(LoginActivity.this, "Already logged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "You can login now", Toast.LENGTH_SHORT).show();
        }
    }
}
/*package com.example.logggin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextLoginEmail, editTextLoginPwd;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;
    private static final String TAG = "LoginActivity";

    private static final String ADMIN_EMAIL = "nasimac300@gmail.com";
    private static final String ADMIN_PASSWORD = "admin123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Login");
        }
        editTextLoginEmail = findViewById(R.id.editText_login_email);
        editTextLoginPwd = findViewById(R.id.editText_login_pwd);
        progressBar = findViewById(R.id.progressbar);
        authProfile = FirebaseAuth.getInstance();

        Button buttonForgotPwd = findViewById(R.id.button_forgot_pwd);
        buttonForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "You can reset your password now.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

        ImageView imageViewShowHidePwd = findViewById(R.id.imageView_show_hide_pwd);
        imageViewShowHidePwd.setImageResource(R.drawable.ic_hide_pwd);
        imageViewShowHidePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextLoginPwd.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    editTextLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageViewShowHidePwd.setImageResource(R.drawable.ic_hide_pwd);
                } else {
                    editTextLoginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowHidePwd.setImageResource(R.drawable.ic_show_pwd);
                }
            }
        });

        Button btnLoginUser = findViewById(R.id.btnLoginUser);
        btnLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        Button btnLoginAdmin = findViewById(R.id.btnLoginAdmin);
        btnLoginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAdmin();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loginUser() {
        String email = editTextLoginEmail.getText().toString();
        String pwd = editTextLoginPwd.getText().toString();
        if (validateInputs(email, pwd)) {
            progressBar.setVisibility(View.VISIBLE);
            authProfile.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = authProfile.getCurrentUser();
                        if (firebaseUser != null && firebaseUser.isEmailVerified()) {
                            Toast.makeText(LoginActivity.this, "You are logged in now", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this, UserProfileActivity.class));
                            finish();
                        } else {
                            handleUnverifiedEmail();
                        }
                    } else {
                        handleLoginFailure(task);
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    private void loginAdmin() {
        String email = editTextLoginEmail.getText().toString();
        String pwd = editTextLoginPwd.getText().toString();
        if (validateInputs(email, pwd)) {
            if (email.equals(ADMIN_EMAIL) && pwd.equals(ADMIN_PASSWORD)) {
                progressBar.setVisibility(View.VISIBLE);
                authProfile.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = authProfile.getCurrentUser();
                            if (firebaseUser != null && firebaseUser.isEmailVerified()) {
                                Toast.makeText(LoginActivity.this, "You are logged in now", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                                finish();
                            } else {
                                handleUnverifiedEmail();
                            }
                        } else {
                            handleLoginFailure(task);
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
            } else {
                Toast.makeText(LoginActivity.this, "Invalid admin credentials", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean validateInputs(String email, String pwd) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(LoginActivity.this, "Please enter your email", Toast.LENGTH_LONG).show();
            editTextLoginEmail.setError("Email is required");
            editTextLoginEmail.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(LoginActivity.this, "Please re-enter your email", Toast.LENGTH_LONG).show();
            editTextLoginEmail.setError("Valid Email is required");
            editTextLoginEmail.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_LONG).show();
            editTextLoginPwd.setError("Password is required");
            editTextLoginPwd.requestFocus();
            return false;
        }
        return true;
    }

    private void handleUnverifiedEmail() {
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification();
            authProfile.signOut();
            showAlertDialog();
        }
    }

    private void handleLoginFailure(@NonNull Task<AuthResult> task) {
        try {
            throw task.getException();
        } catch (FirebaseAuthInvalidUserException e) {
            editTextLoginEmail.setError("User does not exist or is no longer valid. Please register again");
            editTextLoginEmail.requestFocus();
        } catch (FirebaseAuthInvalidCredentialsException e) {
            editTextLoginEmail.setError("Invalid Credentials. Kindly check and re-enter");
            editTextLoginEmail.requestFocus();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Email not verified");
        builder.setMessage("Please verify your email. You cannot login without email verification");
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authProfile.getCurrentUser() != null) {
            Toast.makeText(LoginActivity.this, "Already logged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, UserProfileActivity.class));
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "You can login now", Toast.LENGTH_SHORT).show();
        }
    }
}*/