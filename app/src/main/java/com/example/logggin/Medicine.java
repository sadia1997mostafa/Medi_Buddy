package com.example.logggin;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Medicine implements Parcelable {
    private String id;
    private String name;
    private String genericName;
    private List<String> categories;
    private String activeIngredient;
    private double price;
    private double pricePerUnit;
    private double pricePerStrip;
    private int unitsPerStrip;
    private String companyName;
    private String imagePath;

    // Default constructor required for calls to DataSnapshot.getValue(Medicine.class)
    public Medicine() {
    }

    // Constructor with all fields
    public Medicine(String id, String name, String genericName, List<String> categories, String activeIngredient, double price, double pricePerUnit, double pricePerStrip, int unitsPerStrip, String companyName, String imagePath) {
        this.id = id;
        this.name = name;
        this.genericName = genericName;
        this.categories = categories;
        this.activeIngredient = activeIngredient;
        this.price = price;
        this.pricePerUnit = pricePerUnit;
        this.pricePerStrip = pricePerStrip;
        this.unitsPerStrip = unitsPerStrip;
        this.companyName = companyName;
        this.imagePath = imagePath;
    }

    // Getters and setters for all fields
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getActiveIngredient() {
        return activeIngredient;
    }

    public void setActiveIngredient(String activeIngredient) {
        this.activeIngredient = activeIngredient;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public double getPricePerStrip() {
        return pricePerStrip;
    }

    public void setPricePerStrip(double pricePerStrip) {
        this.pricePerStrip = pricePerStrip;
    }

    public int getUnitsPerStrip() {
        return unitsPerStrip;
    }

    public void setUnitsPerStrip(int unitsPerStrip) {
        this.unitsPerStrip = unitsPerStrip;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // Parcelable implementation
    protected Medicine(Parcel in) {
        id = in.readString();
        name = in.readString();
        genericName = in.readString();
        categories = in.createStringArrayList();
        activeIngredient = in.readString();
        price = in.readDouble();
        pricePerUnit = in.readDouble();
        pricePerStrip = in.readDouble();
        unitsPerStrip = in.readInt();
        companyName = in.readString();
        imagePath = in.readString();
    }

    public static final Creator<Medicine> CREATOR = new Creator<Medicine>() {
        @Override
        public Medicine createFromParcel(Parcel in) {
            return new Medicine(in);
        }

        @Override
        public Medicine[] newArray(int size) {
            return new Medicine[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(genericName);
        dest.writeStringList(categories);
        dest.writeString(activeIngredient);
        dest.writeDouble(price);
        dest.writeDouble(pricePerUnit);
        dest.writeDouble(pricePerStrip);
        dest.writeInt(unitsPerStrip);
        dest.writeString(companyName);
        dest.writeString(imagePath);
    }
}