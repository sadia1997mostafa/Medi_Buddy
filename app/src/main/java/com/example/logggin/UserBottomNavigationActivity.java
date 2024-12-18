package com.example.logggin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserBottomNavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bottom_navigation); // Ensure this matches your XML file name

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    startActivity(new Intent(UserBottomNavigationActivity.this, HomeActivity.class));
                    return true;
                } else if (itemId == R.id.nav_cart) {
                    loadFragment(new CartFragment());
                    return true;
                } else if (itemId == R.id.nav_search) {
                    loadFragment(new SearchFragment());
                    return true;
                } else if (itemId == R.id.nav_notification) {
                    loadFragment(new NotificationFragment());
                    return true;
                }
                return false;
            }
        });

        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }
}