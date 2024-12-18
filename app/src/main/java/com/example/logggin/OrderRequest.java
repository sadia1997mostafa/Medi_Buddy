package com.example.logggin;

import java.io.Serializable;
import java.util.List;

public class OrderRequest implements Serializable {
    private String orderId;
    private String medicineId;
    private String productName;
    private String pricePerMl;
    private String pricePerStrip;
    private String userEmail;
    private String userLocation;
    private String userId;
    private double totalPrice;
    private List<CartItem> cartItems;
    private String status;

    // No-argument constructor
    public OrderRequest() {
    }

    // Constructor with all arguments
    public OrderRequest(String orderId, String medicineId, String productName, String pricePerMl, String pricePerStrip,
                        String userEmail, String userLocation, String userId, double totalPrice, List<CartItem> cartItems, String status) {
        this.orderId = orderId;
        this.medicineId = medicineId;
        this.productName = productName;
        this.pricePerMl = pricePerMl;
        this.pricePerStrip = pricePerStrip;
        this.userEmail = userEmail;
        this.userLocation = userLocation;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.cartItems = cartItems;
        this.status = status;
    }

    // Getter methods
    public String getOrderId() {
        return orderId;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public String getProductName() {
        return productName;
    }

    public String getPricePerMl() {
        return pricePerMl;
    }

    public String getPricePerStrip() {
        return pricePerStrip;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public String getUserId() {
        return userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public String getStatus() {
        return status;
    }
}
