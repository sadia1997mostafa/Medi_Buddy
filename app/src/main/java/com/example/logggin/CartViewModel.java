
package com.example.logggin;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class CartViewModel extends AndroidViewModel {
    private MutableLiveData<List<CartItem>> cartItems;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private static final String CART_ITEMS_KEY = "cart_items";

    public CartViewModel(@NonNull Application application) {
        super(application);
        sharedPreferences = application.getSharedPreferences("cart_prefs", Application.MODE_PRIVATE);
        gson = new GsonBuilder().registerTypeAdapter(Uri.class, new UriTypeAdapter()).create();
        cartItems = new MutableLiveData<>();
        loadCartItems();
    }

    public MutableLiveData<List<CartItem>> getCartItems() {
        return cartItems;
    }

    public void addToCart(CartItem cartItem) {
        List<CartItem> currentItems = cartItems.getValue();
        currentItems.add(cartItem);
        cartItems.setValue(currentItems);
        saveCartItems();
    }

    public void removeFromCart(CartItem cartItem) {
        List<CartItem> currentItems = cartItems.getValue();
        currentItems.remove(cartItem);
        cartItems.setValue(currentItems);
        saveCartItems();
    }

    public void saveCartItems() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(cartItems.getValue());
        editor.putString(CART_ITEMS_KEY, json);
        editor.apply();
    }

    private void loadCartItems() {
        String json = sharedPreferences.getString(CART_ITEMS_KEY, null);
        if (json != null) {
            Type type = new TypeToken<List<CartItem>>() {}.getType();
            List<CartItem> items = gson.fromJson(json, type);
            cartItems.setValue(items);
        }
    }

    public double getTotalPrice() {
        List<CartItem> currentItems = cartItems.getValue();
        double totalPrice = 0.0;
        if (currentItems != null) {
            for (CartItem item : currentItems) {
                totalPrice += item.getPricePerStrip() * item.getQuantity();
            }
        }
        return totalPrice;
    }
}