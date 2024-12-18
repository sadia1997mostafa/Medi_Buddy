package com.example.logggin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MedicineListActivity extends AppCompatActivity implements MedicineListAdapter.OnItemClickListener {
    private FirebaseAuth authProfile;
    private RecyclerView recyclerView;
    private MedicineListAdapter medicineListAdapter;
    private List<Medicine> medicineList;
    private DatabaseReference databaseMedicines;
    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);
        authProfile = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.dark_blue)); // Set the toolbar's color
        toolbar.setTitle(""); // Remove the title
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycler_view_medicine);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        medicineList = new ArrayList<>();
        medicineListAdapter = new MedicineListAdapter(medicineList, this);
        recyclerView.setAdapter(medicineListAdapter);

        // Initialize CartViewModel
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        // Add click listeners for category card views
        findViewById(R.id.card_prescription_medicine).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_herbal_medicine).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_homeopathic_medicine).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_pain_relief).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_cold_flu).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_digestive_health).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_vitamins_supplements).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_allergy_sinus).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_dermatology).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_mental_health).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_cardiovascular_health).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_diabetes_care).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_orthopedic_care).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_diabetes_management).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_hypertension).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_arthritis).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_asthma_respiratory).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_immunity_boosters).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_chronic_pain).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_seasonal_allergies).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_stomach_gut_health).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_childrens_medicine).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_adult_medicine).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_pregnancy_maternity).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_tablets_capsules).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_syrups_liquids).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_topical_creams_ointments).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_injections).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_drops).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_first_aid_bandages).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_medical_equipment).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_skin_care1).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_hair_care).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_mask_gloves).setOnClickListener(this::onCategoryClick);
        findViewById(R.id.card_womans_care).setOnClickListener(this::onCategoryClick);
        // Add more categories as needed

        databaseMedicines = FirebaseDatabase.getInstance().getReference("medicines");
        fetchMedicines("All Medicines");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.nav_home) {
                Intent intent = new Intent(this, MedicineListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_cart) {
                selectedFragment = new CartFragment();
            }
            else if (item.getItemId() == R.id.nav_notification) {
                selectedFragment = new NotificationFragment();
            }
            else if(item.getItemId() == R.id.nav_search) {
                selectedFragment = new SearchFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });
    }

    private void onCategoryClick(View view) {
        String category = "";
        int id = view.getId();
        if (id == R.id.card_prescription_medicine) {
            category = "Prescription Medicine";
        } else if (id == R.id.card_herbal_medicine) {
            category = "Herbal Medicine";
        } else if (id == R.id.card_homeopathic_medicine) {
            category = "Homeopathic Medicine";
        }
        else if (id == R.id.card_skin_care1) {
            category = "Skin Care";
        } else if (id == R.id.card_hair_care) {
            category = "Hair Care";
        } else if (id == R.id.card_mask_gloves) {
            category = "Mask and Gloves";
        } else if (id == R.id.card_womans_care) {
            category = "Woman's Care";
        }
        else if (id == R.id.card_pain_relief) {
            category = "Pain Relief";
        } else if (id == R.id.card_cold_flu) {
            category = "Cold and Flu";
        } else if (id == R.id.card_digestive_health) {
            category = "Digestive Health";
        } else if (id == R.id.card_vitamins_supplements) {
            category = "Vitamins and Supplements";
        } else if (id == R.id.card_allergy_sinus) {
            category = "Allergy and Sinus";
        } else if (id == R.id.card_dermatology) {
            category = "Dermatology";
        } else if (id == R.id.card_mental_health) {
            category = "Mental Health";
        } else if (id == R.id.card_cardiovascular_health) {
            category = "Cardiovascular Health";
        } else if (id == R.id.card_diabetes_care) {
            category = "Diabetes Care";
        } else if (id == R.id.card_orthopedic_care) {
            category = "Orthopedic Care";
        } else if (id == R.id.card_diabetes_management) {
            category = "Diabetes Management";
        } else if (id == R.id.card_hypertension) {
            category = "Hypertension";
        } else if (id == R.id.card_arthritis) {
            category = "Arthritis";
        } else if (id == R.id.card_asthma_respiratory) {
            category = "Asthma and Respiratory";
        } else if (id == R.id.card_immunity_boosters) {
            category = "Immunity Boosters";
        } else if (id == R.id.card_chronic_pain) {
            category = "Chronic Pain";
        } else if (id == R.id.card_seasonal_allergies) {
            category = "Seasonal Allergies";
        } else if (id == R.id.card_stomach_gut_health) {
            category = "Stomach and Gut Health";
        } else if (id == R.id.card_childrens_medicine) {
            category = "Children's Medicine";
        } else if (id == R.id.card_adult_medicine) {
            category = "Adult Medicine";
        } else if (id == R.id.card_pregnancy_maternity) {
            category = "Pregnancy and Maternity";
        } else if (id == R.id.card_tablets_capsules) {
            category = "Tablets and Capsules";
        } else if (id == R.id.card_syrups_liquids) {
            category = "Syrups and Liquids";
        } else if (id == R.id.card_topical_creams_ointments) {
            category = "Topical Creams and Ointments";
        } else if (id == R.id.card_injections) {
            category = "Injections";
        } else if (id == R.id.card_drops) {
            category = "Drops (Eye/Ear)";
        } else if (id == R.id.card_first_aid_bandages) {
            category = "First Aid and Bandages";
        } else if (id == R.id.card_medical_equipment) {
            category = "Medical Equipment";
        }

        Intent intent = new Intent(this, CategoryMedicineListActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
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
            Intent intent = new Intent(MedicineListActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_change_password) {
            Intent intent = new Intent(MedicineListActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_delete_profile) {
            showConfirmationDialog();
        } else if (id == R.id.menu_logout) {
            authProfile.signOut();
            Toast.makeText(MedicineListActivity.this, "Logged out", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MedicineListActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(MedicineListActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
    private void fetchMedicines(String category) {
        databaseMedicines.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                medicineList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Medicine medicine = postSnapshot.getValue(Medicine.class);
                    if (category.equals("All Medicines") || medicine.getCategories().contains(category)) {
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
            // Add selected medicine to cart
            CartItem cartItem = new CartItem(
                    medicine.getImagePath(), // Pass the image path as a String
                    Uri.parse(medicine.getName()), // Convert the name to a Uri
                    String.valueOf(medicine.getPricePerStrip()), // Convert the price per strip to a String
                    medicine.getPrice(), // Ensure this is the correct field
                    1
            );
            cartViewModel.addToCart(cartItem);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();

        // Navigate to MedicineDetailActivity
            Intent intent = new Intent(this, MedicineDetailActivity.class);
            intent.putExtra("medicine", medicine);
            startActivity(intent);
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
                            Toast.makeText(MedicineListActivity.this, "Profile deleted successfully", Toast.LENGTH_SHORT).show();
                            authProfile.signOut();
                            Intent intent = new Intent(MedicineListActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MedicineListActivity.this, "Failed to delete profile", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(MedicineListActivity.this, "Failed to delete user data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    }
