package com.adrcourseassignment.services.favorite;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.adrcourseassignment.dataModels.FavoriteJoke;

@Database(entities = {FavoriteJoke.class}, version = 1)
public abstract class FavoriteJokesDatabase extends RoomDatabase {

    private static FavoriteJokesDatabase instance;
    public abstract FavoriteJokesDAO noteDao();

    public static synchronized FavoriteJokesDatabase getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavoriteJokesDatabase.class, "favorite_joke_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

