package com.example.logggin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class CartItem implements Parcelable {
    private String medicineId;
    private Uri imageUri;
    private String productName;
    private double pricePerStrip;
    private double pricePerMl;
    private int quantity;

    public CartItem(String medicineId, Uri imageUri, String productName, double pricePerStrip, double pricePerMl, int quantity) {
        this.medicineId = medicineId;
        this.imageUri = imageUri;
        this.productName = productName;
        this.pricePerStrip = pricePerStrip;
        this.pricePerMl = pricePerMl;
        this.quantity = quantity;
    }

    // New constructor
    public CartItem(String medicineId, Uri imageUri, String productName, double pricePerStrip, int quantity) {
        this(medicineId, imageUri, productName, pricePerStrip, 0.0, quantity);
    }

    // Getters and setters
    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPricePerStrip() {
        return pricePerStrip;
    }

    public void setPricePerStrip(double pricePerStrip) {
        this.pricePerStrip = pricePerStrip;
    }

    public double getPricePerMl() {
        return pricePerMl;
    }

    public void setPricePerMl(double pricePerMl) {
        this.pricePerMl = pricePerMl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Parcelable implementation
    protected CartItem(Parcel in) {
        medicineId = in.readString();
        imageUri = in.readParcelable(Uri.class.getClassLoader());
        productName = in.readString();
        pricePerStrip = in.readDouble();
        pricePerMl = in.readDouble();
        quantity = in.readInt();
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(medicineId);
        dest.writeParcelable(imageUri, flags);
        dest.writeString(productName);
        dest.writeDouble(pricePerStrip);
        dest.writeDouble(pricePerMl);
        dest.writeInt(quantity);
    }
}