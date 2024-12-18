package com.example.logggin;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {

    private TextView textViewOrderDetails, textViewCustomerEmail, textViewCustomerLocation, textViewTotalPrice;
    private LinearLayout productDetailsLayout;
    private Button buttonConfirmOrder, buttonCancelOrder;
    private DatabaseReference orderDatabaseReference, orderrequestDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        // Initialize UI elements
        textViewOrderDetails = findViewById(R.id.text_view_order_details);
        textViewCustomerEmail = findViewById(R.id.text_view_customer_email);
        textViewCustomerLocation = findViewById(R.id.text_view_customer_location);
        textViewTotalPrice = findViewById(R.id.text_view_total_price);
        productDetailsLayout = findViewById(R.id.product_details_layout);
        buttonConfirmOrder = findViewById(R.id.button_confirm_order);
        buttonCancelOrder = findViewById(R.id.button_cancel_order);

        OrderRequest orderRequest = (OrderRequest) getIntent().getSerializableExtra("orderRequest");

        if (orderRequest != null) {
            Log.d("OrderDetailActivity", "Order data: " + orderRequest.toString());
            textViewOrderDetails.setText("Order Details");
            textViewCustomerEmail.setText("Email: " + orderRequest.getUserEmail());
            textViewCustomerLocation.setText("Location: " + orderRequest.getUserLocation());
            textViewTotalPrice.setText("Total Price: " + orderRequest.getTotalPrice() + " tk");

            if (orderRequest.getCartItems() != null && !orderRequest.getCartItems().isEmpty()) {
                displayMultipleProducts(orderRequest.getCartItems());
            } else {
                displaySingleProduct(orderRequest);
            }

            buttonConfirmOrder.setOnClickListener(v -> {
                Toast.makeText(OrderDetailActivity.this, "Order confirmed", Toast.LENGTH_SHORT).show();
            });

            buttonCancelOrder.setOnClickListener(v -> {
                sendNotification(orderRequest.getUserEmail(), "Order Cancelled", "Your order has been cancelled.");
                Toast.makeText(OrderDetailActivity.this, "Order cancelled", Toast.LENGTH_SHORT).show();
                removeOrder(orderRequest.getOrderId());
            });
        } else {
            Log.e("OrderDetailActivity", "Order data is null");
        }
    }

    private void displaySingleProduct(OrderRequest orderRequest) {
        addProductDetail("Product Name: " + orderRequest.getProductName());
        addProductDetail("Product ID: " + orderRequest.getMedicineId());
        addProductDetail("Price (For mL/L): " + orderRequest.getPricePerMl());
        addProductDetail("Price (Per Strip): " + orderRequest.getPricePerStrip());
    }

    private void displayMultipleProducts(List<CartItem> cartItems) {
        for (CartItem item : cartItems) {
            Log.d("OrderDetailActivity", "Displaying product: " + item.getProductName());
            addProductDetail("Product Name: " + item.getProductName());
            addProductDetail("Product ID: " + item.getMedicineId());
            addProductDetail("Price (For mL/L): " + item.getPricePerMl());
            addProductDetail("Price (Per Strip): " + item.getPricePerStrip());
            addProductDetail("\n");
        }
    }

    private void addProductDetail(String detail) {
        TextView textView = new TextView(this);
        textView.setText(detail);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        productDetailsLayout.addView(textView);
        Log.d("OrderDetailActivity", "Added TextView with detail: " + detail);
    }

    private void sendNotification(String userEmail, String title, String message) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("notifications");
        String notificationId = databaseReference.push().getKey();

        Notification notification = new Notification(title, message, System.currentTimeMillis());
        if (notificationId != null) {
            databaseReference.child(userEmail.replace("@", "_").replace(".", "_")).child(notificationId).setValue(notification)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d("OrderDetailActivity", "Notification saved: " + title + " - " + message);
                        } else {
                            Log.e("OrderDetailActivity", "Failed to save notification", task.getException());
                        }
                    });
        }
    }

    private void removeOrder(String orderId) {
        orderDatabaseReference = FirebaseDatabase.getInstance().getReference("orders").child(orderId);
        orderrequestDatabaseReference = FirebaseDatabase.getInstance().getReference("orderrequest").child(orderId);
        orderDatabaseReference.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("OrderDetailActivity", "Order removed: " + orderId);
            } else {
                Log.e("OrderDetailActivity", "Failed to remove order", task.getException());
            }
        });
        orderrequestDatabaseReference.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("OrderDetailActivity", "Order removed: " + orderId);
            } else {
                Log.e("OrderDetailActivity", "Failed to remove order", task.getException());
            }
        });
    }
}
