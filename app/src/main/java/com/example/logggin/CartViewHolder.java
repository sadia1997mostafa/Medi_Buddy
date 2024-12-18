package com.example.logggin;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class CartViewHolder extends RecyclerView.ViewHolder {
    ImageView imageViewProduct;
    TextView textViewProductName, textViewPricePerStrip, textViewPricePerMl;
    Button buttonDecreaseQuantity, buttonIncreaseQuantity, buttonRemove;
    TextView textViewQuantity;

    public CartViewHolder(View itemView) {
        super(itemView);
        imageViewProduct = itemView.findViewById(R.id.image_view_product);
        textViewProductName = itemView.findViewById(R.id.text_view_product_name);
        textViewPricePerStrip = itemView.findViewById(R.id.text_view_price_per_strip);
        textViewPricePerMl = itemView.findViewById(R.id.text_view_price_per_ml);
        buttonDecreaseQuantity = itemView.findViewById(R.id.button_decrease_quantity);
        buttonIncreaseQuantity = itemView.findViewById(R.id.button_increase_quantity);
        buttonRemove = itemView.findViewById(R.id.button_remove);
        textViewQuantity = itemView.findViewById(R.id.text_view_quantity);
    }
}