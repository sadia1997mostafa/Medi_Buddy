package com.example.logggin;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddMedicineActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText editTextMedicineId, editTextName, editTextGenericName, editTextActiveIngredient, editTextPrice, editTextPricePerUnit, editTextUnitsPerStrip, editTextCompanyName, editTextPricePerStrip;
    private Button buttonSelectCategories, buttonAddMedicine, buttonSelectImage;
    private ImageView imageView;
    private Uri imageUri;
    private List<String> selectedCategories;
    private DatabaseReference databaseMedicines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        editTextMedicineId = findViewById(R.id.edit_text_medicine_id);
        editTextName = findViewById(R.id.edit_text_name);
        editTextGenericName = findViewById(R.id.edit_text_generic_name);
        editTextActiveIngredient = findViewById(R.id.edit_text_active_ingredient);
        editTextPrice = findViewById(R.id.edit_text_price);
        editTextPricePerUnit = findViewById(R.id.edit_text_price_per_unit);
        editTextUnitsPerStrip = findViewById(R.id.edit_text_units_per_strip);
        editTextCompanyName = findViewById(R.id.edit_text_company_name);
        editTextPricePerStrip = findViewById(R.id.edit_text_price_per_strip);
        buttonSelectCategories = findViewById(R.id.button_select_categories);
        buttonAddMedicine = findViewById(R.id.button_add_medicine);
        buttonSelectImage = findViewById(R.id.button_select_image);
        imageView = findViewById(R.id.image_view);

        selectedCategories = new ArrayList<>();
        databaseMedicines = FirebaseDatabase.getInstance().getReference("medicines");

        buttonSelectCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectCategoriesDialog();
            }
        });

        buttonAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndAddMedicine();
            }
        });

        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        // Add TextWatcher to calculate price per strip
        TextWatcher priceWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculatePricePerStrip();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        editTextPricePerUnit.addTextChangedListener(priceWatcher);
        editTextUnitsPerStrip.addTextChangedListener(priceWatcher);
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
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

    private void showSelectCategoriesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_select_categories, null);
        builder.setView(dialogView);

        CheckBox checkboxPrescription = dialogView.findViewById(R.id.checkbox_prescription);
        CheckBox checkboxOtc = dialogView.findViewById(R.id.checkbox_otc);
        CheckBox checkboxHerbal = dialogView.findViewById(R.id.checkbox_herbal);
        CheckBox checkboxHomeopathic = dialogView.findViewById(R.id.checkbox_homeopathic);
        CheckBox checkboxAyurvedic = dialogView.findViewById(R.id.checkbox_ayurvedic);
        CheckBox checkboxPainRelief = dialogView.findViewById(R.id.checkbox_pain_relief);
        CheckBox checkboxColdFlu = dialogView.findViewById(R.id.checkbox_cold_flu);
        CheckBox checkboxDigestiveHealth = dialogView.findViewById(R.id.checkbox_digestive_health);
        CheckBox checkboxAllergySinus = dialogView.findViewById(R.id.checkbox_allergy_sinus);
        CheckBox checkboxVitaminsSupplements = dialogView.findViewById(R.id.checkbox_vitamins_supplements);
        CheckBox checkboxSkinCare = dialogView.findViewById(R.id.checkbox_skin_care);
        CheckBox checkboxMentalHealth = dialogView.findViewById(R.id.checkbox_mental_health);
        CheckBox checkboxCardiovascularHealth = dialogView.findViewById(R.id.checkbox_cardiovascular_health);
        CheckBox checkboxDiabetesCare = dialogView.findViewById(R.id.checkbox_diabetes_care);
        CheckBox checkboxOrthopedicCare = dialogView.findViewById(R.id.checkbox_orthopedic_care);
        CheckBox checkboxDiabetesManagement = dialogView.findViewById(R.id.checkbox_diabetes_management);
        CheckBox checkboxHypertension = dialogView.findViewById(R.id.checkbox_hypertension);
        CheckBox checkboxArthritis = dialogView.findViewById(R.id.checkbox_arthritis);
        CheckBox checkboxAsthma = dialogView.findViewById(R.id.checkbox_asthma);
        CheckBox checkboxImmunityBoosters = dialogView.findViewById(R.id.checkbox_immunity_boosters);
        CheckBox checkboxChronicPain = dialogView.findViewById(R.id.checkbox_chronic_pain);
        CheckBox checkboxSeasonalAllergies = dialogView.findViewById(R.id.checkbox_seasonal_allergies);
        CheckBox checkboxStomachGutHealth = dialogView.findViewById(R.id.checkbox_stomach_gut_health);
        CheckBox checkboxChildrenMedicine = dialogView.findViewById(R.id.checkbox_children_medicine);
        CheckBox checkboxAdultMedicine = dialogView.findViewById(R.id.checkbox_adult_medicine);
        CheckBox checkboxSeniorMedicine = dialogView.findViewById(R.id.checkbox_senior_medicine);
        CheckBox checkboxPregnancyMaternity = dialogView.findViewById(R.id.checkbox_pregnancy_maternity);
        CheckBox checkboxTabletsCapsules = dialogView.findViewById(R.id.checkbox_tablets_capsules);
        CheckBox checkboxSyrupsLiquids = dialogView.findViewById(R.id.checkbox_syrups_liquids);
        CheckBox checkboxTopicalCreams = dialogView.findViewById(R.id.checkbox_topical_creams);
        CheckBox checkboxInjections = dialogView.findViewById(R.id.checkbox_injections);
        CheckBox checkboxPatches = dialogView.findViewById(R.id.checkbox_patches);
        CheckBox checkboxDrops = dialogView.findViewById(R.id.checkbox_drops);
        CheckBox checkboxDiabeticSupplies = dialogView.findViewById(R.id.checkbox_diabetic_supplies);
        CheckBox checkboxFirstAid = dialogView.findViewById(R.id.checkbox_first_aid);
        CheckBox checkboxPersonalCare = dialogView.findViewById(R.id.checkbox_personal_care);
        CheckBox checkboxMedicalEquipment = dialogView.findViewById(R.id.checkbox_medical_equipment);

        builder.setPositiveButton("OK", (dialog, which) -> {
            selectedCategories.clear();
            if (checkboxPrescription.isChecked()) selectedCategories.add("Prescription Medicines");
            if (checkboxOtc.isChecked()) selectedCategories.add("Over-the-Counter (OTC) Medicines");
            if (checkboxHerbal.isChecked()) selectedCategories.add("Herbal and Natural Remedies");
            if (checkboxHomeopathic.isChecked()) selectedCategories.add("Homeopathic Medicines");
            if (checkboxAyurvedic.isChecked()) selectedCategories.add("Ayurvedic Medicines");
            if (checkboxPainRelief.isChecked()) selectedCategories.add("Pain Relief");
            if (checkboxColdFlu.isChecked()) selectedCategories.add("Cold and Flu");
            if (checkboxDigestiveHealth.isChecked()) selectedCategories.add("Digestive Health");
            if (checkboxAllergySinus.isChecked()) selectedCategories.add("Allergy and Sinus");
            if (checkboxVitaminsSupplements.isChecked()) selectedCategories.add("Vitamins and Supplements");
            if (checkboxSkinCare.isChecked()) selectedCategories.add("Skin Care and Dermatology");
            if (checkboxMentalHealth.isChecked()) selectedCategories.add("Mental Health and Stress Relief");
            if (checkboxCardiovascularHealth.isChecked()) selectedCategories.add("Cardiovascular Health");
            if (checkboxDiabetesCare.isChecked()) selectedCategories.add("Diabetes Care");
            if (checkboxOrthopedicCare.isChecked()) selectedCategories.add("Orthopedic Care");
            if (checkboxDiabetesManagement.isChecked()) selectedCategories.add("Diabetes Management");
            if (checkboxHypertension.isChecked()) selectedCategories.add("Hypertension");
            if (checkboxArthritis.isChecked()) selectedCategories.add("Arthritis");
            if (checkboxAsthma.isChecked()) selectedCategories.add("Asthma and Respiratory Disorders");
            if (checkboxImmunityBoosters.isChecked()) selectedCategories.add("Immunity Boosters");
            if (checkboxChronicPain.isChecked()) selectedCategories.add("Chronic Pain");
            if (checkboxSeasonalAllergies.isChecked()) selectedCategories.add("Seasonal Allergies");
            if (checkboxStomachGutHealth.isChecked()) selectedCategories.add("Stomach and Gut Health");
            if (checkboxChildrenMedicine.isChecked()) selectedCategories.add("Children's Medicine");
            if (checkboxAdultMedicine.isChecked()) selectedCategories.add("Adult Medicine");
            if (checkboxSeniorMedicine.isChecked()) selectedCategories.add("Senior Medicine");
            if (checkboxPregnancyMaternity.isChecked()) selectedCategories.add("Pregnancy and Maternity");
            if (checkboxTabletsCapsules.isChecked()) selectedCategories.add("Tablets and Capsules");
            if (checkboxSyrupsLiquids.isChecked()) selectedCategories.add("Syrups and Liquids");
            if (checkboxTopicalCreams.isChecked()) selectedCategories.add("Topical Creams and Ointments");
            if (checkboxInjections.isChecked()) selectedCategories.add("Injections");
            if (checkboxPatches.isChecked()) selectedCategories.add("Patches");
            if (checkboxDrops.isChecked()) selectedCategories.add("Drops (Eye/Ear)");
            if (checkboxDiabeticSupplies.isChecked()) selectedCategories.add("Diabetic Supplies");
            if (checkboxFirstAid.isChecked()) selectedCategories.add("First Aid and Bandages");
            if (checkboxPersonalCare.isChecked()) selectedCategories.add("Personal Care Products");
            if (checkboxMedicalEquipment.isChecked()) selectedCategories.add("Medical Equipment (e.g., thermometers, blood pressure monitors)");
        });

        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    private void checkAndAddMedicine() {
        String medicineId = editTextMedicineId.getText().toString().trim();
        if (medicineId.isEmpty()) {
            Toast.makeText(this, "Please enter a Medicine ID", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseMedicines.child(medicineId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    editTextMedicineId.setError("This ID already exists");
                } else {
                    addMedicine(medicineId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddMedicineActivity.this, "Database error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addMedicine(String medicineId) {
        String name = editTextName.getText().toString().trim();
        String generic = editTextGenericName.getText().toString().trim();
        String active = editTextActiveIngredient.getText().toString().trim();
        String priceStr = editTextPrice.getText().toString().trim();
        String priceUnitStr = editTextPricePerUnit.getText().toString().trim();
        String unitsStr = editTextUnitsPerStrip.getText().toString().trim();
        String company = editTextCompanyName.getText().toString().trim();

        if (name.isEmpty() || generic.isEmpty() || active.isEmpty() || priceStr.isEmpty() || priceUnitStr.isEmpty() || unitsStr.isEmpty() || company.isEmpty() || selectedCategories.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);
        double priceUnit = Double.parseDouble(priceUnitStr);
        int units = Integer.parseInt(unitsStr);
        double priceStrip = priceUnit * units;

        // Save image locally
        String imagePath = saveImageLocally(imageUri);

        Medicine medicine = new Medicine(medicineId, name, generic, selectedCategories, active, price, priceUnit, priceStrip, units, company, imagePath);
        databaseMedicines.child(medicineId).setValue(medicine);

        Toast.makeText(this, "Medicine added", Toast.LENGTH_SHORT).show();
        finish();
    }

    private String saveImageLocally(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            File file = new File(getFilesDir(), "images");
            if (!file.exists()) {
                file.mkdir();
            }
            File imageFile = new File(file, System.currentTimeMillis() + ".jpg");
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}