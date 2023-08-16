package com.example.dietmanagement;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


// Database column info
@Entity(tableName = "weight")
public class EntityWeight implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "monthAndYear")
    private String monthAndYear;

    @ColumnInfo(name = "date")
    private int date;

    @ColumnInfo(name = "weight")
    private String weight;


    // Constructor
    public EntityWeight(String monthAndYear, int date, String weight) {
        this.monthAndYear = monthAndYear;
        this.date = date;
        this.weight = weight;
    }
    protected EntityWeight(Parcel in) {
        id = in.readInt();
        monthAndYear = in.readString();
        date = in.readInt();
        weight = in.readString();
    }

    public static final Creator<EntityWeight> CREATOR = new Creator<EntityWeight>() {
        @Override
        public EntityWeight createFromParcel(Parcel in) {
            return new EntityWeight(in);
        }

        @Override
        public EntityWeight[] newArray(int size) {
            return new EntityWeight[size];
        }
    };

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMonthAndYear() {
        return monthAndYear;
    }

    public void setMonthAndYear(String monthAndYear) {
        this.monthAndYear = monthAndYear;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(monthAndYear);
        dest.writeInt(date);
        dest.writeString(weight);
    }
}