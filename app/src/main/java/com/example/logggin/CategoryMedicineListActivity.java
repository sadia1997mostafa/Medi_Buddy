package com.example.logggin;

import android.content.Intent;
import android.os.Bundle;
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

public class CategoryMedicineListActivity extends AppCompatActivity implements MedicineAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private MedicineAdapter medicineListAdapter;
    private List<Medicine> medicineList;
    private DatabaseReference databaseMedicines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_medicine_list);

        recyclerView = findViewById(R.id.recycler_view_category_medicine);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        medicineList = new ArrayList<>();
        medicineListAdapter = new MedicineAdapter(medicineList, this, this);
        recyclerView.setAdapter(medicineListAdapter);

        databaseMedicines = FirebaseDatabase.getInstance().getReference("medicines");

        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        fetchMedicines(category);
    }

    private void fetchMedicines(String category) {
        databaseMedicines.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                medicineList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Medicine medicine = postSnapshot.getValue(Medicine.class);
                    if (medicine.getCategories().contains(category)) {
                        medicineList.add(medicine);
                    }
                }
                medicineListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onItemClick(Medicine medicine) {
        Intent intent = new Intent(this, MedicineDetailActivity.class);
        intent.putExtra("medicine", medicine);
        startActivity(intent);
    }
}