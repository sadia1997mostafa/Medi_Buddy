// Product.java
package com.example.logggin;

public class Product {
    private String name;
    private String id;
    private double pricePerMlOrL;
    private double pricePerStrip;

    public Product() {
        // Default constructor required for calls to DataSnapshot.getValue(Product.class)
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPricePerMlOrL() {
        return pricePerMlOrL;
    }

    public void setPricePerMlOrL(double pricePerMlOrL) {
        this.pricePerMlOrL = pricePerMlOrL;
    }

    public double getPricePerStrip() {
        return pricePerStrip;
    }

    public void setPricePerStrip(double pricePerStrip) {
        this.pricePerStrip = pricePerStrip;
    }
}