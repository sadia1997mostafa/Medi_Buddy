package com.example.logggin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {

    private TextView textViewWelcome,textViewFullName,textViewEmail,textViewDOB,textViewGender,textViewMobile;
    private ProgressBar progressBar;
    private String fullname,email,dob,gender,mobile;
    private ImageView imageView;
    private FirebaseAuth authProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("User Profile");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        textViewWelcome=findViewById(R.id.textView_show_wlcome);
        textViewFullName=findViewById(R.id.textview_show_full_name);
        textViewEmail=findViewById(R.id.textview_show_email);
        textViewDOB=findViewById(R.id.textview_show_dob);
        textViewGender=findViewById(R.id.textview_show_gender);
        textViewMobile=findViewById(R.id.textview_show_mobile);
        progressBar=findViewById(R.id.progressbar);

      /*  imageView=findViewById(R.id.imageView_profile_dp);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserProfileActivity.this,UploadProfilePictureActivity.class);
                startActivity(intent);
            }
        });*/
        authProfile=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=authProfile.getCurrentUser();
        if(firebaseUser==null)
        {
            Toast.makeText(UserProfileActivity.this,"Something went wrong! User's details are not available  at the moment.",Toast.LENGTH_LONG).show();

        }else {
            chechifEmailisverified(firebaseUser);

            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UserProfileActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void chechifEmailisverified(FirebaseUser firebaseUser) {
        if(!firebaseUser.isEmailVerified())
        {
            showAlertDialog();
        }
    }
    private void showAlertDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(UserProfileActivity.this);
        builder.setTitle("Email not verified");
        builder.setMessage("Please verify your email. You cannot login without email verification next time");
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    private void showUserProfile(@NonNull FirebaseUser firebaseUser) {
        String userid=firebaseUser.getUid();
        DatabaseReference referenceProfile= FirebaseDatabase.getInstance().getReference("Registered Users");
        referenceProfile.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readuserdetails=snapshot.getValue(ReadWriteUserDetails.class);
                if(readuserdetails!=null)
                {
                    fullname=firebaseUser.getDisplayName();
                    email=firebaseUser.getEmail();
                    dob=readuserdetails.dob;
                    gender=readuserdetails.gender;
                    mobile=readuserdetails.mobile;

                    textViewWelcome.setText("Welcome "+fullname+" !");
                    textViewFullName.setText(fullname);
                    textViewEmail.setText(email);
                    textViewDOB.setText(dob);
                    textViewGender.setText(gender);
                    textViewMobile.setText(mobile);

                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfileActivity.this,"Something went wrong !",Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_refresh) {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
            return true; // Indicate that the event has been handled
        }else if(id==R.id.menu_update_profile)
        {
            Intent intent=new Intent(UserProfileActivity.this,UpdateProfileActivity.class);
            startActivity(intent);
        }
        else if(id==R.id.menu_change_password)
        {
            Intent intent=new Intent(UserProfileActivity.this,ChangePasswordActivity.class);
            startActivity(intent);
        }
        else if(id==R.id.menu_delete_profile)
        {
            showConfirmationDialog();
        }
        else if(id==R.id.menu_logout)
        {
            authProfile.signOut();
            Toast.makeText(UserProfileActivity.this,"Logged out",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(UserProfileActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }
        else if(id==android.R.id.home)
        {
            Intent intent = new Intent(UserProfileActivity.this, HomeActivity.class);
            startActivity(intent);

            finish();
            return true;
        }
        else {
            Toast.makeText(UserProfileActivity.this,"Something went Wrong!",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item); // Use the superclass method for other cases
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
                            Toast.makeText(UserProfileActivity.this, "Profile deleted successfully", Toast.LENGTH_SHORT).show();
                            authProfile.signOut();
                            Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(UserProfileActivity.this, "Failed to delete profile", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(UserProfileActivity.this, "Failed to delete user data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}