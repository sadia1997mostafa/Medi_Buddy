package com.example.logggin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ConfirmOrderActivity extends AppCompatActivity {
    private TextView textViewProductName, textViewProductPrice1, textViewProductPrice2, textViewTotalPrice;
    private EditText editTextLocation;
    private Button buttonConfirmOrder;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private List<CartItem> cartItemList;
    private double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        // Initialize UI elements
        textViewProductName = findViewById(R.id.text_view_product_name);
        textViewProductPrice1 = findViewById(R.id.text_view_product_price1);
        textViewProductPrice2 = findViewById(R.id.text_view_product_price2);
        textViewTotalPrice = findViewById(R.id.text_view_total_price);
        editTextLocation = findViewById(R.id.edit_text_location);
        buttonConfirmOrder = findViewById(R.id.button_confirm_order);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Retrieve product details from the Intent
        cartItemList = getIntent().getParcelableArrayListExtra("cartItems");
        totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);

        if (cartItemList != null && !cartItemList.isEmpty()) {
            // Handle multiple products
            displayMultipleProducts();
        } else {
            // Handle single product
            handleSingleProduct();
        }

        // Set OnClickListener for the confirm order button
        buttonConfirmOrder.setOnClickListener(v -> confirmOrder());
    }

    private void handleSingleProduct() {
        String medicineId = getIntent().getStringExtra("medicineId");
        String productName = getIntent().getStringExtra("productName");
        String productPrice1 = getIntent().getStringExtra("price (For mL/L)");
        String productPrice2 = getIntent().getStringExtra("Price Per Strip");

        if (productPrice1 == null) productPrice1 = "0";
        if (productPrice2 == null) productPrice2 = "0";

        try {
            double price1 = Double.parseDouble(productPrice1);
            double price2 = Double.parseDouble(productPrice2);
            totalPrice = price1 + price2;

            textViewProductName.setText(productName);
            textViewProductPrice1.setText("Price (For mL/L): " + productPrice1);
            textViewProductPrice2.setText("Price per Strip: " + productPrice2);
            textViewTotalPrice.setText("Total Price: " + totalPrice + " tk");

        } catch (NumberFormatException e) {
            Log.e("ConfirmOrderActivity", "Invalid price format", e);
            Toast.makeText(this, "Invalid price data", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayMultipleProducts() {
        Log.d("ConfirmOrderActivity", "Displaying multiple products");
        StringBuilder productDetails = new StringBuilder();
        for (CartItem item : cartItemList) {
            Log.d("ConfirmOrderActivity", "Product: " + item.getProductName() + ", Price per Strip: " + item.getPricePerStrip());
            productDetails.append("Product: ").append(item.getProductName())
                    .append("\nPrice per Strip: ").append(item.getPricePerStrip())
                    .append("\n\n");
        }
        textViewProductName.setText(productDetails.toString());
        textViewTotalPrice.setText("Total Price: " + totalPrice + " tk");
    }

    private void confirmOrder() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "User not authenticated. Please log in.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = user.getUid();
        String userEmail = user.getEmail();
        String userLocation = editTextLocation.getText().toString();
        String orderId = mDatabase.child("orderRequests").push().getKey(); // Generate a unique order ID

        if (cartItemList != null && !cartItemList.isEmpty()) {
            for (CartItem item : cartItemList) {
                OrderRequest orderRequest = new OrderRequest(orderId, item.getMedicineId(), item.getProductName(),
                        String.valueOf(item.getPricePerMl()), String.valueOf(item.getPricePerStrip()),
                        userEmail, userLocation, userId, totalPrice, cartItemList, "Pending");
                saveOrderRequest(orderRequest);
            }
        } else {
            String medicineId = getIntent().getStringExtra("medicineId");
            String productName = getIntent().getStringExtra("productName");
            String productPrice1 = getIntent().getStringExtra("price (For mL/L)");
            String productPrice2 = getIntent().getStringExtra("Price Per Strip");

            OrderRequest orderRequest = new OrderRequest(orderId, medicineId, productName, productPrice1, productPrice2,
                    userEmail, userLocation, userId, totalPrice, new ArrayList<>(), "Pending");
            saveOrderRequest(orderRequest);
        }
    }

    private void saveOrderRequest(OrderRequest orderRequest) {
        mDatabase.child("orderRequests").push().setValue(orderRequest)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mDatabase.child("pendingOrders").push().setValue(orderRequest)
                                .addOnCompleteListener(pendingTask -> {
                                    if (pendingTask.isSuccessful()) {
                                        Toast.makeText(this, "Order request sent to admin", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(this, "Failed to add to pending orders", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(this, "Failed to send order request", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

/*package com.example.logggin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ConfirmOrderActivity extends AppCompatActivity {
    private TextView textViewProductName, textViewProductPrice1, textViewProductPrice2, textViewTotalPrice;
    private EditText editTextLocation;
    private Button buttonConfirmOrder;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private List<CartItem> cartItemList;
    private double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        // Initialize UI elements
        textViewProductName = findViewById(R.id.text_view_product_name);
        textViewProductPrice1 = findViewById(R.id.text_view_product_price1);
        textViewProductPrice2 = findViewById(R.id.text_view_product_price2);
        textViewTotalPrice = findViewById(R.id.text_view_total_price);
        editTextLocation = findViewById(R.id.edit_text_location);
        buttonConfirmOrder = findViewById(R.id.button_confirm_order);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Retrieve product details from the Intent
        cartItemList = getIntent().getParcelableArrayListExtra("cartItems");
        totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);

        if (cartItemList != null && !cartItemList.isEmpty()) {
            // Handle multiple products
            displayMultipleProducts();
        } else {
            // Handle single product
            handleSingleProduct();
        }

        // Set OnClickListener for the confirm order button
        buttonConfirmOrder.setOnClickListener(v -> confirmOrder());
    }

    private void handleSingleProduct() {
        String medicineId = getIntent().getStringExtra("medicineId");
        String productName = getIntent().getStringExtra("productName");
        String productPrice1 = getIntent().getStringExtra("price (For mL/L)");
        String productPrice2 = getIntent().getStringExtra("Price Per Strip");

        if (productPrice1 == null) productPrice1 = "0";
        if (productPrice2 == null) productPrice2 = "0";

        try {
            double price1 = Double.parseDouble(productPrice1);
            double price2 = Double.parseDouble(productPrice2);
            totalPrice = price1 + price2;

            textViewProductName.setText(productName);
            textViewProductPrice1.setText("Price (For mL/L): " + productPrice1);
            textViewProductPrice2.setText("Price per Strip: " + productPrice2);
            textViewTotalPrice.setText("Total Price: " + totalPrice + " tk");

        } catch (NumberFormatException e) {
            Log.e("ConfirmOrderActivity", "Invalid price format", e);
            Toast.makeText(this, "Invalid price data", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayMultipleProducts() {
        Log.d("ConfirmOrderActivity", "Displaying multiple products");
        StringBuilder productDetails = new StringBuilder();
        for (CartItem item : cartItemList) {
            Log.d("ConfirmOrderActivity", "Product: " + item.getProductName() + ", Price per Strip: " + item.getPricePerStrip());
            productDetails.append("Product: ").append(item.getProductName())
                    .append("\nPrice per Strip: ").append(item.getPricePerStrip())
                    .append("\n\n");
        }
        textViewProductName.setText(productDetails.toString());
        textViewTotalPrice.setText("Total Price: " + totalPrice + " tk");
    }

    private void confirmOrder() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "User not authenticated. Please log in.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = user.getUid();
        String userEmail = user.getEmail();
        String userLocation = editTextLocation.getText().toString();
        String orderId = mDatabase.child("orderRequests").push().getKey(); // Generate a unique order ID

        if (cartItemList != null && !cartItemList.isEmpty()) {
            for (CartItem item : cartItemList) {
                OrderRequest orderRequest = new OrderRequest(orderId, item.getMedicineId(), item.getProductName(),
                        String.valueOf(item.getPricePerMl()), String.valueOf(item.getPricePerStrip()),
                        userEmail, userLocation, userId, totalPrice, cartItemList);
                saveOrderRequest(orderRequest);
            }
        } else {
            String medicineId = getIntent().getStringExtra("medicineId");
            String productName = getIntent().getStringExtra("productName");
            String productPrice1 = getIntent().getStringExtra("price (For mL/L)");
            String productPrice2 = getIntent().getStringExtra("Price Per Strip");

            OrderRequest orderRequest = new OrderRequest(orderId, medicineId, productName, productPrice1, productPrice2,
                    userEmail, userLocation, userId, totalPrice, new ArrayList<>());
            saveOrderRequest(orderRequest);
        }
    }

    private void saveOrderRequest(OrderRequest orderRequest) {
        mDatabase.child("orderRequests").push().setValue(orderRequest)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mDatabase.child("pendingOrders").push().setValue(orderRequest)
                                .addOnCompleteListener(pendingTask -> {
                                    if (pendingTask.isSuccessful()) {
                                        Toast.makeText(this, "Order request sent to admin", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(this, "Failed to add to pending orders", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(this, "Failed to send order request", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
+/
/*package com.example.logggin;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ConfirmOrderActivity extends AppCompatActivity {
    private TextView textViewProductName, textViewProductPrice1, textViewProductPrice2, textViewTotalPrice;
    private EditText editTextLocation;
    private Button buttonConfirmOrder;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private List<CartItem> cartItemList;
    private double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        // Initialize UI elements
        textViewProductName = findViewById(R.id.text_view_product_name);
        textViewProductPrice1 = findViewById(R.id.text_view_product_price1);
        textViewProductPrice2 = findViewById(R.id.text_view_product_price2);
        textViewTotalPrice = findViewById(R.id.text_view_total_price);
        editTextLocation = findViewById(R.id.edit_text_location);
        buttonConfirmOrder = findViewById(R.id.button_confirm_order);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Retrieve product details from the Intent
        cartItemList = getIntent().getParcelableArrayListExtra("cartItems");
        totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);

        if (cartItemList != null && !cartItemList.isEmpty()) {
            // Handle multiple products
            displayMultipleProducts();
        } else {
            // Handle single product
            handleSingleProduct();
        }

        // Set OnClickListener for the confirm order button
        buttonConfirmOrder.setOnClickListener(v -> confirmOrder());
    }

    private void handleSingleProduct() {
        String medicineId = getIntent().getStringExtra("medicineId");
        String productName = getIntent().getStringExtra("productName");
        String productPrice1 = getIntent().getStringExtra("price (For mL/L)");
        String productPrice2 = getIntent().getStringExtra("Price Per Strip");

        if (productPrice1 == null) productPrice1 = "0";
        if (productPrice2 == null) productPrice2 = "0";

        try {
            double price1 = Double.parseDouble(productPrice1);
            double price2 = Double.parseDouble(productPrice2);
            totalPrice = price1 + price2;

            textViewProductName.setText(productName);
            textViewProductPrice1.setText("Price (For mL/L): " + productPrice1);
            textViewProductPrice2.setText("Price per Strip: " + productPrice2);
            textViewTotalPrice.setText("Total Price: " + totalPrice + " tk");

        } catch (NumberFormatException e) {
            Log.e("ConfirmOrderActivity", "Invalid price format", e);
            Toast.makeText(this, "Invalid price data", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayMultipleProducts() {
        Log.d("ConfirmOrderActivity", "Displaying multiple products");
        StringBuilder productDetails = new StringBuilder();
        for (CartItem item : cartItemList) {
            Log.d("ConfirmOrderActivity", "Product: " + item.getProductName() + ", Price per Strip: " + item.getPricePerStrip());
            productDetails.append("Product: ").append(item.getProductName())
                    .append("\nPrice per Strip: ").append(item.getPricePerStrip())
                    .append("\n\n");
        }
        textViewProductName.setText(productDetails.toString());
        textViewTotalPrice.setText("Total Price: " + totalPrice + " tk");
    }

    private void confirmOrder() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "User not authenticated. Please log in.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = user.getUid();
        String userEmail = user.getEmail();
        String userLocation = editTextLocation.getText().toString();

        if (cartItemList != null && !cartItemList.isEmpty()) {
            for (CartItem item : cartItemList) {
                OrderRequest orderRequest = new OrderRequest(item.getMedicineId(), item.getProductName(),
                        String.valueOf(item.getPricePerMl()), String.valueOf(item.getPricePerStrip()),
                        userEmail, userLocation, userId, totalPrice);
                saveOrderRequest(orderRequest);
            }
        } else {
            String medicineId = getIntent().getStringExtra("medicineId");
            String productName = getIntent().getStringExtra("productName");
            String productPrice1 = getIntent().getStringExtra("price (For mL/L)");
            String productPrice2 = getIntent().getStringExtra("Price Per Strip");

            OrderRequest orderRequest = new OrderRequest(medicineId, productName, productPrice1, productPrice2,
                    userEmail, userLocation, userId, totalPrice);
            saveOrderRequest(orderRequest);
        }
    }

    private void saveOrderRequest(OrderRequest orderRequest) {
        mDatabase.child("orderRequests").push().setValue(orderRequest)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mDatabase.child("pendingOrders").push().setValue(orderRequest)
                                .addOnCompleteListener(pendingTask -> {
                                    if (pendingTask.isSuccessful()) {
                                        Toast.makeText(this, "Order request sent to admin", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(this, "Failed to add to pending orders", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(this, "Failed to send order request", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
*/







/*package com.example.logggin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfirmOrderActivity extends AppCompatActivity {
    private TextView textViewProductName, textViewProductPrice1, textViewProductPrice2;
    private EditText editTextLocation;
    private Button buttonConfirmOrder;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        textViewProductName = findViewById(R.id.text_view_product_name);
        textViewProductPrice1 = findViewById(R.id.text_view_product_price1);
        textViewProductPrice2 = findViewById(R.id.text_view_product_price2);
        editTextLocation = findViewById(R.id.edit_text_location);
        buttonConfirmOrder = findViewById(R.id.button_confirm_order);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Retrieve product details from intent
        String medicineId = getIntent().getStringExtra("medicineId");
        String productName = getIntent().getStringExtra("productName");
        String productPrice1 = getIntent().getStringExtra("price (For mL/L)");
        String productPrice2 = getIntent().getStringExtra("Price Per Strip");

        // Display product details
        textViewProductName.setText(productName);
        textViewProductPrice1.setText("Price (For mL/L): " + productPrice1);
        textViewProductPrice2.setText("Price per Strip: " + productPrice2);

        // Set OnClickListener for the confirm order button
        buttonConfirmOrder.setOnClickListener(v -> {
            // Display toast message
            Toast.makeText(ConfirmOrderActivity.this, "Please wait a moment for admin approval", Toast.LENGTH_SHORT).show();

            // Get current user
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                String userName = user.getDisplayName();
                String userId = user.getUid();

                // Create order request
                OrderRequest orderRequest = new OrderRequest(medicineId, productName, userName, userId);

                // Save order request to Firebase
                mDatabase.child("orderRequests").push().setValue(orderRequest)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Add to pending orders
                                mDatabase.child("pendingOrders").push().setValue(orderRequest)
                                        .addOnCompleteListener(pendingTask -> {
                                            if (pendingTask.isSuccessful()) {
                                                Toast.makeText(ConfirmOrderActivity.this, "Order request sent to admin", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(ConfirmOrderActivity.this, "Failed to add to pending orders", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Toast.makeText(ConfirmOrderActivity.this, "Failed to send order request", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    public static class OrderRequest {
        public String medicineId;
        public String productName;
        public String userName;
        public String userId;

        public OrderRequest() {
            // Default constructor required for calls to DataSnapshot.getValue(OrderRequest.class)
        }

        public OrderRequest(String medicineId, String productName, String userName, String userId) {
            this.medicineId = medicineId;
            this.productName = productName;
            this.userName = userName;
            this.userId = userId;
        }
    }
}*/