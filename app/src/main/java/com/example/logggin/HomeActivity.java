package com.example.logggin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        authProfile = FirebaseAuth.getInstance();

        CardView doctorAppointment = findViewById(R.id.cardDoctorAppointment);
        doctorAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, DoctorAppointmentActivity.class));
            }
        });

        CardView findHospital = findViewById(R.id.cardFindHospital);
        findHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, FindHospitalActivity.class));
            }
        });
        CardView buymedicine = findViewById(R.id.cardBuyMedicine);
        buymedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, MedicineListActivity.class));
            }
        });

        CardView article = findViewById(R.id.cardOrderDetails);
        article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ArticleActivity.class));
            }
        });
        CardView bmi = findViewById(R.id.BMICalculator);
        bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, BmiCalculatorActivity.class));
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_refresh) {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
            return true;
        } else if (id == R.id.menu_update_profile) {
            Intent intent = new Intent(HomeActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_change_password) {
            Intent intent = new Intent(HomeActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_delete_profile) {
            showConfirmationDialog();
        } else if (id == R.id.menu_logout) {
            authProfile.signOut();
            Toast.makeText(HomeActivity.this, "Logged out", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(HomeActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity(); // Close the app
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Profile");
        builder.setMessage("Do you really want to delete your profile?");
        builder.setPositiveButton("Yes", (dialog, which) -> deleteUserProfile());
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteUserProfile() {
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        if (firebaseUser != null) {
            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
            referenceProfile.child(firebaseUser.getUid()).removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    firebaseUser.delete().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Toast.makeText(HomeActivity.this, "Profile deleted successfully", Toast.LENGTH_SHORT).show();
                            authProfile.signOut();
                            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(HomeActivity.this, "Failed to delete profile", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(HomeActivity.this, "Failed to delete user data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}


/*package com.example.logggin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        CardView doctorAppointment = findViewById(R.id.cardDoctorAppointment);
        doctorAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, DoctorAppointmentActivity.class));
            }
        });

        CardView findHospital = findViewById(R.id.cardFindHospital);
        findHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, FindHospitalActivity.class));
            }
        });
        CardView buymedicine = findViewById(R.id.cardBuyMedicine);
        buymedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, MedicineListActivity.class));
            }
        });

        CardView article = findViewById(R.id.cardOrderDetails);
        article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,ArticleActivity.class));
            }
        });
        CardView bmi = findViewById(R.id.BMICalculator);
        bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, BmiCalculatorActivity.class));
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}*/