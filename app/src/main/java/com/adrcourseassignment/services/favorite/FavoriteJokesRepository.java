package com.adrcourseassignment.services.favorite;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.adrcourseassignment.dataModels.FavoriteJoke;

import java.util.List;

public class FavoriteJokesRepository {

    private FavoriteJokesDAO favoriteJokesDAO;
    private static FavoriteJokesRepository instance;
    private LiveData<List<FavoriteJoke>> jokes;

    public static synchronized FavoriteJokesRepository getInstance(Application application){
        if(instance == null) instance = new  FavoriteJokesRepository(application);
        return instance;
    }

    private FavoriteJokesRepository(Application application){
        FavoriteJokesDatabase database = FavoriteJokesDatabase.getInstance(application);
        favoriteJokesDAO = database.noteDao();
        jokes = favoriteJokesDAO.getAll();
    }

    public LiveData<List<FavoriteJoke>> getAllJokes(){
        return jokes;
    }

    public void insert(FavoriteJoke favoriteJoke){
        new InsertIntoFavoriteJokesAsync(favoriteJokesDAO).execute(favoriteJoke);
    }

    private static class InsertIntoFavoriteJokesAsync extends AsyncTask<FavoriteJoke,Void,Void> {
        private FavoriteJokesDAO favoriteJokesDAO;

        private InsertIntoFavoriteJokesAsync(FavoriteJokesDAO favoriteJokesDAO){
            this.favoriteJokesDAO = favoriteJokesDAO;
        }
        @Override
        protected Void doInBackground(FavoriteJoke... favoriteJokes) {
            favoriteJokesDAO.insert(favoriteJokes[0]);
            return null;
        }
    }
}
