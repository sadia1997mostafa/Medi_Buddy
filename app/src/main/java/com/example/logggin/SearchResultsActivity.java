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

public class SearchResultsActivity extends AppCompatActivity implements MedicineListAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private MedicineListAdapter medicineListAdapter;
    private List<Medicine> medicineList;
    private DatabaseReference databaseMedicines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        recyclerView = findViewById(R.id.recycler_view_search_results);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        medicineList = new ArrayList<>();
        medicineListAdapter = new MedicineListAdapter(medicineList, this);
        recyclerView.setAdapter(medicineListAdapter);

        databaseMedicines = FirebaseDatabase.getInstance().getReference("medicines");

        String query = getIntent().getStringExtra("query");
        fetchMedicines(query);
    }

    private void fetchMedicines(String query) {
        databaseMedicines.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                medicineList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Medicine medicine = postSnapshot.getValue(Medicine.class);
                    if (medicine.getName().toLowerCase().contains(query.toLowerCase())) {
                        medicineList.add(medicine);
                    }
                }
                medicineListAdapter.notifyDataSetChanged();
                if (medicineList.isEmpty()) {
                    Toast.makeText(SearchResultsActivity.this, "No matching items found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onItemClick(Medicine medicine) {
        // Handle item click
    }
}