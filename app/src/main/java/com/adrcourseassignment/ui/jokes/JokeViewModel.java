package com.adrcourseassignment.ui.jokes;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.adrcourseassignment.dataModels.FavoriteJoke;
import com.adrcourseassignment.services.favorite.FavoriteJokesRepository;
import com.adrcourseassignment.services.jokes.JokesProvider;

import java.util.ArrayList;
import java.util.List;

public class JokeViewModel extends AndroidViewModel {

    private FavoriteJokesRepository favoriteJokesRepository;
    private MutableLiveData<List<MutableLiveData<String>>> jokes;

    public JokeViewModel(Application application) {
        super(application);
        initialJokesPopulation();
        favoriteJokesRepository = favoriteJokesRepository.getInstance(application);
    }

    private void initialJokesPopulation(){
        jokes = new MutableLiveData<List<MutableLiveData<String>>>(new ArrayList());
        ArrayList arrList = new ArrayList<String>();
        for(int i = 0; i < 20; i ++){
            MutableLiveData<String> str = new MutableLiveData<>("Loading ...");
            JokesProvider.updateTargetWithNewJoke(str);
            arrList.add(str);
        }
        jokes.setValue(arrList);
    }

    public void addMoreJokes(){
        List<MutableLiveData<String>> _jokes =  jokes.getValue();
        for(int i = 0; i < 20; i ++){
            MutableLiveData<String> str = new MutableLiveData<>("Loading ...");
            JokesProvider.updateTargetWithNewJoke(str);
            _jokes.add(str);
        }
        jokes.setValue(_jokes);
    }

    public LiveData<List<MutableLiveData<String>>> getJokes(){
        return jokes;
    }

    public void addJokeToFavorite(String content){
        favoriteJokesRepository.insert(new FavoriteJoke(0,content));
    }


}