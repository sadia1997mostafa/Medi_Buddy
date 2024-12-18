
package com.example.logggin;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class PendingRequestActivity extends AppCompatActivity implements OrderRequestAdapter.OnOrderClickListener {
    private RecyclerView recyclerView;
    private OrderRequestAdapter adapter;
    private List<OrderRequest> orderRequestList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_request);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderRequestList = new ArrayList<>();
        adapter = new OrderRequestAdapter(this, orderRequestList, this);
        recyclerView.setAdapter(adapter);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("orderRequests");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderRequestList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    OrderRequest orderRequest = snapshot.getValue(OrderRequest.class);
                    orderRequestList.add(orderRequest);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });

        // Create a notification with PendingIntent
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("productName", "Product Name");
        intent.putExtra("productId", "12345");
        intent.putExtra("productPrice1", "$10.00");
        intent.putExtra("productPrice2", "$10.00");
        intent.putExtra("customerName", "John Doe");
        intent.putExtra("customerEmail", "john.doe@example.com");
        intent.putExtra("customerLocation", "123 Main St, City, Country");

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ? PendingIntent.FLAG_IMMUTABLE : 0
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "your_channel_id")
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle("Pending Request")
                .setContentText("You have a new pending request.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(2, builder.build());
    }

    @Override
    public void onOrderClick(int position) {
        // Handle order click
    }
}