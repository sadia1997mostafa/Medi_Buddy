package com.example.logggin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class UpdateMedicineActivity extends AppCompatActivity {

    private EditText searchMedicine, editTextName, editTextGenericName, editTextActiveIngredient, editTextPrice, editTextPricePerUnit, editTextUnitsPerStrip, editTextPricePerStrip, editTextCompanyName;
    private ListView listViewMedicines;
    private Button buttonUpdateMedicine;
    private DatabaseReference databaseReference;
    private String selectedMedicineId;
    private List<Medicine> medicineList;
    private List<String> selectedCategories;
    private String imagePath; // Declare imagePath variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_medicine);

        TextView textViewTitle = findViewById(R.id.text_view_title);
        searchMedicine = findViewById(R.id.search_medicine);
        listViewMedicines = findViewById(R.id.list_view_medicines);
        editTextName = findViewById(R.id.edit_text_name);
        editTextGenericName = findViewById(R.id.edit_text_generic_name);
        editTextActiveIngredient = findViewById(R.id.edit_text_active_ingredient);
        editTextPrice = findViewById(R.id.edit_text_price);
        editTextPricePerUnit = findViewById(R.id.edit_text_price_per_unit);
        editTextUnitsPerStrip = findViewById(R.id.edit_text_units_per_strip);
        editTextPricePerStrip = findViewById(R.id.edit_text_price_per_strip);
        editTextCompanyName = findViewById(R.id.edit_text_company_name);
        buttonUpdateMedicine = findViewById(R.id.button_update_medicine);

        databaseReference = FirebaseDatabase.getInstance().getReference("medicines");
        medicineList = new ArrayList<>();
        selectedCategories = new ArrayList<>();

        // Add TextWatchers to calculate price per strip
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculatePricePerStrip();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        editTextPricePerUnit.addTextChangedListener(textWatcher);
        editTextUnitsPerStrip.addTextChangedListener(textWatcher);

        searchMedicine.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchMedicine(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        listViewMedicines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Medicine selectedMedicine = medicineList.get(position);
                selectedMedicineId = selectedMedicine.getId();
                fillMedicineDetails(selectedMedicine);
            }
        });

        buttonUpdateMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMedicine();
            }
        });
    }

    private void searchMedicine(String query) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                medicineList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Medicine medicine = snapshot.getValue(Medicine.class);
                    if (medicine != null && (medicine.getName().contains(query) || medicine.getGenericName().contains(query) || medicine.getId().contains(query))) {
                        medicineList.add(medicine);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(UpdateMedicineActivity.this, android.R.layout.simple_list_item_1, getMedicineNames(medicineList));
                listViewMedicines.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdateMedicineActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<String> getMedicineNames(List<Medicine> medicines) {
        List<String> names = new ArrayList<>();
        for (Medicine medicine : medicines) {
            names.add(medicine.getName());
        }
        return names;
    }

    private void fillMedicineDetails(Medicine medicine) {
        editTextName.setText(medicine.getName());
        editTextGenericName.setText(medicine.getGenericName());
        editTextActiveIngredient.setText(medicine.getActiveIngredient());
        editTextPrice.setText(String.valueOf(medicine.getPrice()));
        editTextPricePerUnit.setText(String.valueOf(medicine.getPricePerUnit()));
        editTextUnitsPerStrip.setText(String.valueOf(medicine.getUnitsPerStrip()));
        editTextPricePerStrip.setText(String.valueOf(medicine.getPricePerStrip()));
        editTextCompanyName.setText(medicine.getCompanyName());
        selectedCategories = medicine.getCategories();
        imagePath = medicine.getImagePath(); // Initialize imagePath
    }

    private void calculatePricePerStrip() {
        String pricePerUnitStr = editTextPricePerUnit.getText().toString().trim();
        String unitsPerStripStr = editTextUnitsPerStrip.getText().toString().trim();

        if (!pricePerUnitStr.isEmpty() && !unitsPerStripStr.isEmpty()) {
            double pricePerUnit = Double.parseDouble(pricePerUnitStr);
            int unitsPerStrip = Integer.parseInt(unitsPerStripStr);
            double pricePerStrip = pricePerUnit * unitsPerStrip;
            editTextPricePerStrip.setText(String.valueOf(pricePerStrip));
        } else {
            editTextPricePerStrip.setText("");
        }
    }

    private void updateMedicine() {
        String name = editTextName.getText().toString().trim();
        String generic = editTextGenericName.getText().toString().trim();
        String active = editTextActiveIngredient.getText().toString().trim();
        String priceStr = editTextPrice.getText().toString().trim();
        String priceUnitStr = editTextPricePerUnit.getText().toString().trim();
        String unitsStr = editTextUnitsPerStrip.getText().toString().trim();
        String company = editTextCompanyName.getText().toString().trim();

        if (name.isEmpty() || generic.isEmpty() || active.isEmpty() || priceStr.isEmpty() || priceUnitStr.isEmpty() || unitsStr.isEmpty() || company.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);
        double priceUnit = Double.parseDouble(priceUnitStr);
        int units = Integer.parseInt(unitsStr);
        double priceStrip = priceUnit * units;

        Medicine updatedMedicine = new Medicine(selectedMedicineId, name, generic, selectedCategories, active, price, priceUnit, priceStrip, units, company, imagePath);
        databaseReference.child(selectedMedicineId).setValue(updatedMedicine);

        Toast.makeText(this, "Medicine updated successfully", Toast.LENGTH_SHORT).show();
    }
}