// Order.java
package com.example.logggin;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private String orderId;
    private String userId;
    private Medicine medicine;
    private Customer customer;
    private List<Product> products;

    // Default constructor required for calls to DataSnapshot.getValue(Order.class)
    public Order() {
    }

    public Order(String orderId, String userId, Medicine medicine, Customer customer, List<Product> products) {
        this.orderId = orderId;
        this.userId = userId;
        this.medicine = medicine;
        this.customer = customer;
        this.products = products;
    }

    // Getters and setters for all properties
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}