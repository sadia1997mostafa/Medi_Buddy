package com.example.logggin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
            boolean saveLogin = sharedPreferences.getBoolean("saveLogin", false);

            Intent intent;
            if (saveLogin) {
                intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
            } else {
                intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            }
            startActivity(intent);
            finish();
        }, 2000); // 3 seconds delay
    }
}