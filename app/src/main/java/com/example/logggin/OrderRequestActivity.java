
package com.example.logggin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
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

public class OrderRequestActivity extends AppCompatActivity implements OrderRequestAdapter.OnOrderClickListener {
    private RecyclerView recyclerView;
    private OrderRequestAdapter adapter;
    private List<OrderRequest> orderRequestList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_request);

        recyclerView = findViewById(R.id.recycler_view_order_requests);
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
                    if (orderRequest != null) {
                        orderRequestList.add(orderRequest);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("OrderRequestActivity", "DatabaseError: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void onOrderClick(int position) {
        OrderRequest orderRequest = orderRequestList.get(position);
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("requestId", orderRequest.getOrderId()); // Pass the unique request ID
        startActivityForResult(intent, 1); // Request code = 1
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String requestId = data.getStringExtra("requestId");

            // Find and remove the item from the list using the ID
            if (requestId != null) {
                for (int i = 0; i < orderRequestList.size(); i++) {
                    if (orderRequestList.get(i).getOrderId().equals(requestId)) {
                        orderRequestList.remove(i);
                        adapter.notifyItemRemoved(i);
                        break;
                    }
                }
            }
        }
    }
}

