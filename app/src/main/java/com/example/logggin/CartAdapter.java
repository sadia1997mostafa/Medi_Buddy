package com.example.logggin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItemList;
    private CartViewModel cartViewModel;
    private Runnable updateTotalPriceCallback;

    public CartAdapter(List<CartItem> cartItemList, CartViewModel cartViewModel, Runnable updateTotalPriceCallback) {
        this.cartItemList = cartItemList;
        this.cartViewModel = cartViewModel;
        this.updateTotalPriceCallback = updateTotalPriceCallback;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        holder.bind(cartItem);
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public void updateCartItems(List<CartItem> newCartItems) {
        this.cartItemList = newCartItems;
        notifyDataSetChanged();
        updateTotalPriceCallback.run();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewProduct;
        private TextView textViewProductName, textViewPricePerStrip, textViewPricePerMl, textViewQuantity;
        private Button buttonDecreaseQuantity, buttonIncreaseQuantity, buttonRemove;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.image_view_product);
            textViewProductName = itemView.findViewById(R.id.text_view_product_name);
            textViewPricePerStrip = itemView.findViewById(R.id.text_view_price_per_strip);
            textViewPricePerMl = itemView.findViewById(R.id.text_view_price_per_ml);
            textViewQuantity = itemView.findViewById(R.id.text_view_quantity);
            buttonDecreaseQuantity = itemView.findViewById(R.id.button_decrease_quantity);
            buttonIncreaseQuantity = itemView.findViewById(R.id.button_increase_quantity);
            buttonRemove = itemView.findViewById(R.id.button_remove);
        }

        public void bind(CartItem cartItem) {
            textViewProductName.setText(cartItem.getProductName());
            textViewPricePerStrip.setText("Price per Strip: " + cartItem.getPricePerStrip() + " tk");
            textViewPricePerMl.setText("Price (for mL/L): " + cartItem.getPricePerMl() + " tk");
            textViewQuantity.setText(String.valueOf(cartItem.getQuantity()));
            Glide.with(imageViewProduct.getContext())
                    .load(cartItem.getImageUri())
                    .into(imageViewProduct);

            buttonDecreaseQuantity.setOnClickListener(v -> {
                if (cartItem.getQuantity() > 1) {
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                    textViewQuantity.setText(String.valueOf(cartItem.getQuantity()));
                    cartViewModel.saveCartItems();
                    updateTotalPriceCallback.run();
                }
            });

            buttonIncreaseQuantity.setOnClickListener(v -> {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                textViewQuantity.setText(String.valueOf(cartItem.getQuantity()));
                cartViewModel.saveCartItems();
                updateTotalPriceCallback.run();
            });

            buttonRemove.setOnClickListener(v -> {
                cartViewModel.removeFromCart(cartItem);
                Toast.makeText(v.getContext(), "Item removed from cart", Toast.LENGTH_SHORT).show();
                updateTotalPriceCallback.run();
            });
        }
    }
}


/*package com.example.logggin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItemList;
    private CartViewModel cartViewModel;
    private Runnable updateTotalPriceCallback;

    public CartAdapter(List<CartItem> cartItemList, CartViewModel cartViewModel, Runnable updateTotalPriceCallback) {
        this.cartItemList = cartItemList;
        this.cartViewModel = cartViewModel;
        this.updateTotalPriceCallback = updateTotalPriceCallback;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        holder.bind(cartItem);
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public void updateCartItems(List<CartItem> newCartItems) {
        this.cartItemList = newCartItems;
        notifyDataSetChanged();
        updateTotalPriceCallback.run();
    }

    public List<CartItem> getCartItems() {
        return cartItemList;
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewProduct;
        private TextView textViewProductName, textViewPricePerStrip, textViewPricePerMl, textViewQuantity;
        private Button buttonDecreaseQuantity, buttonIncreaseQuantity, buttonRemove;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.image_view_product);
            textViewProductName = itemView.findViewById(R.id.text_view_product_name);
            textViewPricePerStrip = itemView.findViewById(R.id.text_view_price_per_strip);
            textViewPricePerMl = itemView.findViewById(R.id.text_view_price_per_ml);
            textViewQuantity = itemView.findViewById(R.id.text_view_quantity);
            buttonDecreaseQuantity = itemView.findViewById(R.id.button_decrease_quantity);
            buttonIncreaseQuantity = itemView.findViewById(R.id.button_increase_quantity);
            buttonRemove = itemView.findViewById(R.id.button_remove);
        }

        public void bind(CartItem cartItem) {
            imageViewProduct.setImageURI(cartItem.getImageUri());
            textViewProductName.setText(cartItem.getProductName());
            textViewPricePerStrip.setText("Price per Strip: " + cartItem.getPricePerStrip() + " tk");
            textViewPricePerMl.setText("Price (for mL/L): " + cartItem.getPricePerMl() + " tk");
            textViewQuantity.setText(String.valueOf(cartItem.getQuantity()));

            buttonDecreaseQuantity.setOnClickListener(v -> {
                if (cartItem.getQuantity() > 1) {
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                    textViewQuantity.setText(String.valueOf(cartItem.getQuantity()));
                    cartViewModel.saveCartItems();
                    updateTotalPriceCallback.run();
                }
            });

            buttonIncreaseQuantity.setOnClickListener(v -> {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                textViewQuantity.setText(String.valueOf(cartItem.getQuantity()));
                cartViewModel.saveCartItems();
                updateTotalPriceCallback.run();
            });

            buttonRemove.setOnClickListener(v -> {
                cartViewModel.removeFromCart(cartItem);
                updateTotalPriceCallback.run();
            });
        }
    }
}*/