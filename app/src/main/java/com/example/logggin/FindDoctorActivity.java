package com.example.logggin;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class FindDoctorActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseDoctors;
    private List<Doctor> doctorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);

        recyclerView = findViewById(R.id.recycler_view);
        databaseDoctors = FirebaseDatabase.getInstance().getReference("doctors");
        doctorList = new ArrayList<>();

        // Get the category from the intent
        String category = getIntent().getStringExtra("category");
        if (category != null) {
            fetchDoctorsByCategory(category);
        } else {
            Toast.makeText(this, "No category selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchDoctorsByCategory(String category) {
        databaseDoctors.orderByChild("category").equalTo(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                doctorList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Doctor doctor = postSnapshot.getValue(Doctor.class);
                    doctorList.add(doctor);
                }
                DoctorAdapter adapter = new DoctorAdapter(doctorList, FindDoctorActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(FindDoctorActivity.this));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FindDoctorActivity.this, "Failed to load doctors", Toast.LENGTH_SHORT).show();
            }
        });
    }
}