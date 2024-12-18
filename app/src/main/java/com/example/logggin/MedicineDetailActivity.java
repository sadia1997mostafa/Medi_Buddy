// MedicineDetailActivity.java
package com.example.logggin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;

public class MedicineDetailActivity extends AppCompatActivity {

    private static final String TAG = "MedicineDetailActivity";
    private ImageView imageView;
    private TextView textViewName, textViewGenericName, textViewActiveIngredient, textViewPrice, textViewPricePerUnit, textViewUnitsPerStrip, textViewCompanyName, textViewPricePerStrip;
    private Button buttonAddToCart;
    private CartViewModel cartViewModel;
    private Medicine medicine;
    private Button buyMedicineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_detail);

        imageView = findViewById(R.id.image_view);
        textViewName = findViewById(R.id.text_view_name);
        textViewGenericName = findViewById(R.id.text_view_generic_name);
        textViewActiveIngredient = findViewById(R.id.text_view_active_ingredient);
        textViewPrice = findViewById(R.id.text_view_price);
        textViewPricePerUnit = findViewById(R.id.text_view_price_per_unit);
        textViewUnitsPerStrip = findViewById(R.id.text_view_units_per_strip);
        textViewCompanyName = findViewById(R.id.text_view_company_name);
        textViewPricePerStrip = findViewById(R.id.text_view_price_per_strip);
        buttonAddToCart = findViewById(R.id.button_add_to_cart);
        buyMedicineButton = findViewById(R.id.button_buy_medicine);

        // Assuming medicine is passed as a Parcelable extra
        medicine = getIntent().getParcelableExtra("medicine");

        if (medicine == null) {
            Log.e(TAG, "Medicine object is null");
            return;
        }

        Log.d(TAG, "Medicine received: " + medicine.getName());

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        textViewName.setText(medicine.getName());
        textViewGenericName.setText(medicine.getGenericName());
        textViewActiveIngredient.setText(medicine.getActiveIngredient());
        textViewPrice.setText(String.valueOf(medicine.getPrice()));
        textViewPricePerUnit.setText(String.valueOf(medicine.getPricePerUnit()));
        textViewUnitsPerStrip.setText(String.valueOf(medicine.getUnitsPerStrip()));
        textViewCompanyName.setText(medicine.getCompanyName());
        textViewPricePerStrip.setText(String.valueOf(medicine.getPricePerStrip()));

        // Load image from local storage
        if (medicine.getImagePath() != null) {
            File imgFile = new File(medicine.getImagePath());
            if (imgFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setImageResource(R.drawable.no_profile_pic); // Set a default image if file doesn't exist
            }
        } else {
            imageView.setImageResource(R.drawable.no_profile_pic); // Set a default image if imagePath is null
        }

        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(medicine);
            }
        });

        buyMedicineButton.setOnClickListener(v -> {
            Log.d(TAG, "Buy button clicked");
            Intent intent = new Intent(MedicineDetailActivity.this, ConfirmOrderActivity.class);
            intent.putExtra("medicineId", medicine.getId());
            intent.putExtra("productName", medicine.getName());
            intent.putExtra("price (For mL/L)", String.valueOf(medicine.getPrice()));
            intent.putExtra("Price Per Strip", String.valueOf(medicine.getPricePerStrip()));
            startActivity(intent);
        });
    }

    private void addToCart(Medicine medicine) {
        CartItem cartItem = new CartItem(
                medicine.getId(), // Pass the medicine ID
                Uri.parse(medicine.getImagePath()), // Convert the image path to a Uri
                medicine.getName(), // Pass the name as a String
                medicine.getPricePerStrip(), // Pass the price per strip
                1 // Initial quantity
        );

        cartViewModel.addToCart(cartItem);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
    }


}

/*package com.example.logggin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;

public class MedicineDetailActivity extends AppCompatActivity {

    private static final String TAG = "MedicineDetailActivity";
    private ImageView imageView;
    private TextView textViewName, textViewGenericName, textViewActiveIngredient, textViewPrice, textViewPricePerUnit, textViewUnitsPerStrip, textViewCompanyName, textViewPricePerStrip;
    private Button buttonAddToCart;
    private CartViewModel cartViewModel;
    private Medicine medicine;
    private Button buyMedicineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_detail);

        imageView = findViewById(R.id.image_view);
        textViewName = findViewById(R.id.text_view_name);
        textViewGenericName = findViewById(R.id.text_view_generic_name);
        textViewActiveIngredient = findViewById(R.id.text_view_active_ingredient);
        textViewPrice = findViewById(R.id.text_view_price);
        textViewPricePerUnit = findViewById(R.id.text_view_price_per_unit);
        textViewUnitsPerStrip = findViewById(R.id.text_view_units_per_strip);
        textViewCompanyName = findViewById(R.id.text_view_company_name);
        textViewPricePerStrip = findViewById(R.id.text_view_price_per_strip);
        buttonAddToCart = findViewById(R.id.button_add_to_cart);
        buyMedicineButton = findViewById(R.id.button_buy_medicine);

        // Assuming medicine is passed as a Parcelable extra
        medicine = getIntent().getParcelableExtra("medicine");

        if (medicine == null) {
            Log.e(TAG, "Medicine object is null");
            return;
        }

        Log.d(TAG, "Medicine received: " + medicine.getName());

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        textViewName.setText(medicine.getName());
        textViewGenericName.setText(medicine.getGenericName());
        textViewActiveIngredient.setText(medicine.getActiveIngredient());
        textViewPrice.setText(String.valueOf(medicine.getPrice()));
        textViewPricePerUnit.setText(String.valueOf(medicine.getPricePerUnit()));
        textViewUnitsPerStrip.setText(String.valueOf(medicine.getUnitsPerStrip()));
        textViewCompanyName.setText(medicine.getCompanyName());
        textViewPricePerStrip.setText(String.valueOf(medicine.getPricePerStrip()));

        // Load image from local storage
        if (medicine.getImagePath() != null) {
            File imgFile = new File(medicine.getImagePath());
            if (imgFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setImageResource(R.drawable.no_profile_pic); // Set a default image if file doesn't exist
            }
        } else {
            imageView.setImageResource(R.drawable.no_profile_pic); // Set a default image if imagePath is null
        }

        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(medicine);
            }
        });

        buyMedicineButton.setOnClickListener(v -> {
            Log.d(TAG, "Buy button clicked");
            Intent intent = new Intent(MedicineDetailActivity.this, ConfirmOrderActivity.class);
            intent.putExtra("medicineId", medicine.getId());
            intent.putExtra("productName", medicine.getName());
            intent.putExtra("price (For mL/L)", String.valueOf(medicine.getPrice()));
            intent.putExtra("Price Per Strip", String.valueOf(medicine.getPricePerStrip()));
            startActivity(intent);
        });
    }

    private void addToCart(Medicine medicine) {
        CartItem cartItem = new CartItem(
                medicine.getImagePath(), // Pass the image path as a String
                Uri.parse(medicine.getName()), // Convert the name to a Uri
                String.valueOf(medicine.getPricePerStrip()),
                medicine.getPrice(), // Ensure this is the correct field
                1
        );

        cartViewModel.addToCart(cartItem);
        Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
    }




}*/