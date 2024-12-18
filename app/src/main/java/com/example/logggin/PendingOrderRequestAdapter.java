// PendingOrderRequestAdapter.java
package com.example.logggin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PendingOrderRequestAdapter extends RecyclerView.Adapter<PendingOrderRequestAdapter.PendingOrderRequestViewHolder> {
    private List<Order> pendingOrderList;
    private OnOrderClickListener onOrderClickListener;

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }

    public PendingOrderRequestAdapter(List<Order> pendingOrderList, OnOrderClickListener onOrderClickListener) {
        this.pendingOrderList = pendingOrderList;
        this.onOrderClickListener = onOrderClickListener;
    }

    @NonNull
    @Override
    public PendingOrderRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pending_order_request, parent, false);
        return new PendingOrderRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingOrderRequestViewHolder holder, int position) {
        Order order = pendingOrderList.get(position);
        holder.bind(order, onOrderClickListener);
    }

    @Override
    public int getItemCount() {
        return pendingOrderList.size();
    }

    public static class PendingOrderRequestViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewOrderRequest;

        public PendingOrderRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderRequest = itemView.findViewById(R.id.text_view_order_request);
        }

        public void bind(Order order, OnOrderClickListener onOrderClickListener) {
            textViewOrderRequest.setText("Request to buy medicine/product: " + order.getMedicine().getName());
            itemView.setOnClickListener(v -> onOrderClickListener.onOrderClick(order));
        }
    }
}
