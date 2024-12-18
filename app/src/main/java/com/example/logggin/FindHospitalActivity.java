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

public class FindHospitalActivity extends AppCompatActivity {

    private RecyclerView recyclerViewHospitals;
    private DatabaseReference databaseHospitals;
    private List<Hospital> hospitalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_hospital);

        recyclerViewHospitals = findViewById(R.id.recycler_view_hospitals);
        recyclerViewHospitals.setLayoutManager(new LinearLayoutManager(this));
        databaseHospitals = FirebaseDatabase.getInstance().getReference("hospitals");
        hospitalList = new ArrayList<>();

        fetchHospitals();
    }

    private void fetchHospitals() {
        databaseHospitals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hospitalList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Hospital hospital = postSnapshot.getValue(Hospital.class);
                    hospitalList.add(hospital);
                }
                HospitalList adapter = new HospitalList(FindHospitalActivity.this, hospitalList);
                recyclerViewHospitals.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FindHospitalActivity.this, "Failed to load hospitals", Toast.LENGTH_SHORT).show();
            }
        });
    }
}