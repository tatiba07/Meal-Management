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
public interface DaoWeight {
    @Query("SELECT * FROM weight")
    List<EntityWeight> getAll();
    @Insert
    void insert(EntityWeight weight);
    @Update
    void update(EntityWeight weight);
    @Delete
    void delete(EntityWeight weight);
    // New method for getting weight by ID
    @Query("SELECT * FROM weight WHERE id = :id")
    EntityWeight getById(int id);
    @Query("SELECT * FROM weight WHERE monthAndYear = :monthAndYear")
    List<EntityWeight> getByWeight(String monthAndYear);
    // New method for getting food by name
    @Query("SELECT * FROM weight ORDER BY monthAndYear ASC")
    LiveData<List<EntityWeight>> getAllWeights();
    @Query("UPDATE weight SET monthAndYear = :monthAndYear, date = :date, weight = :weight WHERE monthAndYear = :monthAndYear AND date = :date")
    void update(String monthAndYear, int date, double weight);
    @Query("SELECT weight FROM weight")
    List<String> getWeight();

}