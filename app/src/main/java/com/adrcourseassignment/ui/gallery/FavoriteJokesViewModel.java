package com.adrcourseassignment.ui.gallery;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adrcourseassignment.dataModels.FavoriteJoke;
import com.adrcourseassignment.services.favorite.FavoriteJokesRepository;

import java.util.List;

public class FavoriteJokesViewModel extends AndroidViewModel {

    private FavoriteJokesRepository jokesRepository;



    public FavoriteJokesViewModel(Application app) {
        super(app);
        jokesRepository = FavoriteJokesRepository.getInstance(app);
    }

    public LiveData<List<FavoriteJoke>> getJokes(){
        return jokesRepository.getAllJokes();
    }

    public void removeJoke(int id){
        jokesRepository.remove(id);
    }


}