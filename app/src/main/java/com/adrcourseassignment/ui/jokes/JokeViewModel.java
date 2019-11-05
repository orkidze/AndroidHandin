package com.adrcourseassignment.ui.jokes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adrcourseassignment.services.JokesProvider;

import java.util.ArrayList;
import java.util.List;

public class JokeViewModel extends ViewModel {


    private MutableLiveData<List<MutableLiveData<String>>> jokes;

    public JokeViewModel() {
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


}