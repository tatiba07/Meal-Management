package com.example.dietmanagement;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


// Database column info
@Entity(tableName = "food")
public class EntityFood implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "food_name")
    private String foodName;

    @ColumnInfo(name = "per")
    private int per;

    @ColumnInfo(name = "calorie")
    private double calorie;

    @ColumnInfo(name = "carbon")
    private double carbon;

    @ColumnInfo(name = "protein")
    private double protein;

    @ColumnInfo(name = "fat")
    private double fat;

    // Constructor
    public EntityFood(String foodName, int per, double calorie, double carbon, double protein, double fat) {
        this.foodName = foodName;
        this.per = per;
        this.calorie = calorie;
        this.carbon = carbon;
        this.protein = protein;
        this.fat = fat;
    }
    protected EntityFood(Parcel in) {
        id = in.readInt();
        foodName = in.readString();
        per = in.readInt();
        calorie = in.readDouble();
        carbon = in.readDouble();
        protein = in.readDouble();
        fat = in.readDouble();
    }

    public static final Creator<EntityFood> CREATOR = new Creator<EntityFood>() {
        @Override
        public EntityFood createFromParcel(Parcel in) {
            return new EntityFood(in);
        }

        @Override
        public EntityFood[] newArray(int size) {
            return new EntityFood[size];
        }
    };

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getPer() {
        return per;
    }

    public void setPer(int per) {
        this.per = per;
    }

    public double getCalorie() {
        return calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }

    public double getCarbon() {
        return carbon;
    }

    public void setCarbon(double carbon) {
        this.carbon = carbon;
    }
    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.carbon = protein;
    }
    public double getFat() {
        return fat;
    }
    public void setFat(double fat) {
        this.carbon = fat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(foodName);
        dest.writeInt(per);
        dest.writeDouble(calorie);
        dest.writeDouble(carbon);
        dest.writeDouble(protein);
        dest.writeDouble(fat);
    }
}