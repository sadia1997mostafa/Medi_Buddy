
package com.example.logggin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartFragment extends Fragment {
    private CartViewModel cartViewModel;
    private TextView totalPriceTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        totalPriceTextView = view.findViewById(R.id.total_price_text_view);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        CartAdapter cartAdapter = new CartAdapter(cartViewModel.getCartItems().getValue(), cartViewModel, this::updateTotalPrice);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(cartAdapter);

        cartViewModel.getCartItems().observe(getViewLifecycleOwner(), cartAdapter::updateCartItems);

        // Handle back press
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(getActivity(), MedicineListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        return view;
    }

    private void updateTotalPrice() {
        if (totalPriceTextView != null) {
            double totalPrice = cartViewModel.getTotalPrice();
            totalPriceTextView.setText("Total Price: " + totalPrice + " tk");
        }
    }
}
/*package com.example.logggin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

public class CartFragment extends Fragment {
    private CartViewModel cartViewModel;
    private TextView totalPriceTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        totalPriceTextView = view.findViewById(R.id.total_price_text_view);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        CartAdapter cartAdapter = new CartAdapter(cartViewModel.getCartItems().getValue(), cartViewModel, this::updateTotalPrice);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_cart);
        recyclerView.setAdapter(cartAdapter);

        cartViewModel.getCartItems().observe(getViewLifecycleOwner(), cartAdapter::updateCartItems);

        return view;
    }

    private void updateTotalPrice() {
        double totalPrice = cartViewModel.getTotalPrice();
        totalPriceTextView.setText("Total Price: " + totalPrice + " tk");
    }
}*/