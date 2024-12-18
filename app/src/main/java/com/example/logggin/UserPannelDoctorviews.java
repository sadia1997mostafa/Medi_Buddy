package com.example.logggin;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserPannelDoctorviews extends AppCompatActivity {

    private RecyclerView doctorRecyclerView;
    private DoctorAdapter doctorAdapter;
    private List<Doctor> doctorList;
    private DatabaseReference databaseReference;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pannel_doctorviews);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        category = getIntent().getStringExtra("category");

        doctorRecyclerView = findViewById(R.id.doctor_recycler_view);
        doctorRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        doctorList = new ArrayList<>();
        doctorAdapter = new DoctorAdapter(doctorList, this);
        doctorRecyclerView.setAdapter(doctorAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("doctors");

        fetchDoctors();
    }

    private void fetchDoctors() {
        databaseReference.orderByChild("category").equalTo(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                doctorList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Doctor doctor = snapshot.getValue(Doctor.class);
                    doctorList.add(doctor);
                }
                doctorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserPannelDoctorviews.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}