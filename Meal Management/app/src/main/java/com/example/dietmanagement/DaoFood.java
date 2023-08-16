package com.example.dietmanagement;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// SQL sentences
@Dao
public interface DaoFood {
    @Query("SELECT * FROM food")
    List<EntityFood> getAll();
    @Insert
    void insert(EntityFood food);
    @Update
    void update(EntityFood food);
    @Delete
    void delete(EntityFood food);
    // New method for getting food by ID
    @Query("SELECT * FROM food WHERE id = :id")
    EntityFood getById(int id);
    @Query("SELECT * FROM food WHERE food_name = :foodName")
    EntityFood getByFoodName(String foodName);
    // New method for getting food by name
    @Query("SELECT * FROM food ORDER BY food_name ASC")
    LiveData<List<EntityFood>> getAllFoods();
    @Query("UPDATE food SET per = :newPer, calorie = :newCalorie, carbon = :newCarbon, protein = :newProtein, fat = :newFat WHERE food_name = :foodName")
    void update(String foodName, int newPer, double newCalorie, double newCarbon, double newProtein, double newFat);
    @Query("DELETE FROM food WHERE food_name = :foodName")
    void deleteFoodByName(String foodName);
    @Query("SELECT food_name FROM food")
    List<String> getFoodNames();

}