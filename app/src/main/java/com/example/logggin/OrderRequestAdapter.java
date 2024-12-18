package com.example.logggin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OrderRequestAdapter extends RecyclerView.Adapter<OrderRequestAdapter.OrderRequestViewHolder> {
    private Context context;
    private List<OrderRequest> orderRequestList;
    private OnOrderClickListener onOrderClickListener;
    private SharedPreferences sharedPreferences;

    public OrderRequestAdapter(Context context, List<OrderRequest> orderRequestList, OnOrderClickListener onOrderClickListener) {
        this.context = context;
        this.orderRequestList = orderRequestList;
        this.onOrderClickListener = onOrderClickListener;
        this.sharedPreferences = context.getSharedPreferences("OrderRequestPrefs", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public OrderRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_request, parent, false);
        return new OrderRequestViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull OrderRequestViewHolder holder, int position) {
        OrderRequest orderRequest = orderRequestList.get(position);
        holder.productName.setText(orderRequest.getProductName());
        holder.totalPrice.setText(String.valueOf(orderRequest.getTotalPrice()));

        // Retrieve the processed state from SharedPreferences
        boolean isProcessed = sharedPreferences.getBoolean("processed_" + position, false);
        if (isProcessed) {
            holder.processButton.setText("Processed");
            holder.processButton.setEnabled(false);
            holder.itemView.setEnabled(false);
        } else {
            holder.processButton.setText("Process");
            holder.processButton.setEnabled(true);
            holder.itemView.setEnabled(true);
        }

        holder.processButton.setOnClickListener(v -> {
            if (!sharedPreferences.getBoolean("processed_" + position, false)) {
                holder.processButton.setText("Processed");
                holder.processButton.setEnabled(false);
                holder.itemView.setEnabled(false);

                // Save the processed state in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("processed_" + position, true);
                editor.apply();

                onOrderClickListener.onOrderClick(position);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (holder.itemView.isEnabled()) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("orderRequest", orderRequest);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return orderRequestList.size();
    }

    public static class OrderRequestViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView totalPrice;
        Button processButton;

        public OrderRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.text_view_medicine_name);
            totalPrice = itemView.findViewById(R.id.text_view_total_price);
            processButton = itemView.findViewById(R.id.button_process_order);
        }
    }

    public interface OnOrderClickListener {
        void onOrderClick(int position);
    }
}

/*package com.example.logggin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OrderRequestAdapter extends RecyclerView.Adapter<OrderRequestAdapter.OrderRequestViewHolder> {
    private Context context;
    private List<OrderRequest> orderRequestList;
    private OnOrderClickListener onOrderClickListener;

    public OrderRequestAdapter(Context context, List<OrderRequest> orderRequestList, OnOrderClickListener onOrderClickListener) {
        this.context = context;
        this.orderRequestList = orderRequestList;
        this.onOrderClickListener = onOrderClickListener;
    }

    @NonNull
    @Override
    public OrderRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_request, parent, false);
        return new OrderRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRequestViewHolder holder, int position) {
        OrderRequest orderRequest = orderRequestList.get(position);
        holder.productName.setText(orderRequest.getProductName());
       // holder.userName.setText(orderRequest.getUserName());
        holder.totalPrice.setText(String.valueOf(orderRequest.getTotalPrice()));

        holder.processButton.setOnClickListener(v -> {
            holder.processButton.setText("Processed");
            holder.processButton.setEnabled(false);
            onOrderClickListener.onOrderClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return orderRequestList.size();
    }

    public static class OrderRequestViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView userName;
        TextView totalPrice;
        Button processButton;

        public OrderRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.text_view_medicine_name);
            userName = itemView.findViewById(R.id.text_view_user_name);
            totalPrice = itemView.findViewById(R.id.text_view_total_price);
            processButton = itemView.findViewById(R.id.button_process_order);
        }
    }

    public interface OnOrderClickListener {
        void onOrderClick(int position);
    }
}*/


/*package com.example.logggin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OrderRequestAdapter extends RecyclerView.Adapter<OrderRequestAdapter.OrderRequestViewHolder> {
    private Context context;
    private List<OrderRequest> orderRequestList;
    private OnOrderClickListener onOrderClickListener;

    public OrderRequestAdapter(Context context, List<OrderRequest> orderRequestList, OnOrderClickListener onOrderClickListener) {
        this.context = context;
        this.orderRequestList = orderRequestList;
        this.onOrderClickListener = onOrderClickListener;
    }

    @NonNull
    @Override
    public OrderRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_request, parent, false);
        return new OrderRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRequestViewHolder holder, int position) {
        OrderRequest orderRequest = orderRequestList.get(position);
        holder.productName.setText(orderRequest.getProductName());
        holder.itemView.setOnClickListener(v -> onOrderClickListener.onOrderClick(position));
    }

    @Override
    public int getItemCount() {
        return orderRequestList.size();
    }

    public static class OrderRequestViewHolder extends RecyclerView.ViewHolder {
        TextView productName;

        public OrderRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.text_view_medicine_name);
        }
    }

    public interface OnOrderClickListener {
        void onOrderClick(int position);
    }
}

*/
/*package com.example.logggin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OrderRequestAdapter extends RecyclerView.Adapter<OrderRequestAdapter.OrderRequestViewHolder> {

    private Context context;
    private List<OrderRequest> orderRequestList;
    private OnOrderClickListener onOrderClickListener;

    public OrderRequestAdapter(Context context, List<OrderRequest> orderRequestList, OnOrderClickListener onOrderClickListener) {
        this.context = context;
        this.orderRequestList = orderRequestList;
        this.onOrderClickListener = onOrderClickListener;
    }

    @NonNull
    @Override
    public OrderRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_request, parent, false);
        return new OrderRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRequestViewHolder holder, int position) {
        OrderRequest orderRequest = orderRequestList.get(position);
        holder.productName.setText(orderRequest.getProductName());
       // holder.userName.setText(orderRequest.getUserName());
        holder.itemView.setOnClickListener(v -> onOrderClickListener.onOrderClick(position));
    }

    @Override
    public int getItemCount() {
        return orderRequestList.size();
    }

    public static class OrderRequestViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView userName;

        public OrderRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.text_view_medicine_name);
            userName = itemView.findViewById(R.id.text_view_user_name);
        }
    }

    public interface OnOrderClickListener {
        void onOrderClick(int position);
    }
}*/