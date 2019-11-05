package com.adrcourseassignment.services.favorite;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.adrcourseassignment.dataModels.FavoriteJoke;

import java.util.List;

@Dao
public interface FavoriteJokesDAO {
    @Insert
    void insert(FavoriteJoke favoriteJoke);

    @Update
    void update(FavoriteJoke favoriteJoke);

    @Delete
    void delete(FavoriteJoke favoriteJoke);

    @Query("SELECT * FROM favorite_joke")
    LiveData<List<FavoriteJoke>> getAll();

}
