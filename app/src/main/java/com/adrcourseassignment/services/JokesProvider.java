package com.adrcourseassignment.services;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.adrcourseassignment.dataModels.DadJoke;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JokesProvider {


    private static Random r = new Random();

    public static void updateTargetWithNewJoke(final MutableLiveData<String> dataTarget) {
        switch (r.nextInt(2)) {
            case 0:
                updateTargetWithGeekJoke(dataTarget);
                break;
            case 1:
                updateTargetWithDadJokesAPI(dataTarget);
                break;
            default:
                updateTargetWithDadJokesAPI(dataTarget);

        }

    }

    private static void updateTargetWithGeekJoke(final MutableLiveData<String> dataTarget) {
        GeekJokesAPI geekJokesAPI = ServiceGenerator.getGeekJokesApi();
        Call<String> call = geekJokesAPI.getJoke();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                dataTarget.setValue(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dataTarget.setValue(t.getLocalizedMessage());
            }
        });
    }

    private static void updateTargetWithDadJokesAPI(final MutableLiveData<String> dataTarget) {
        DadJokesAPI dadJokesAPI = ServiceGenerator.getDadJokesAPI();
        Call<DadJoke> call = dadJokesAPI.getJoke();
        call.enqueue(new Callback<DadJoke>() {
            @Override
            public void onResponse(Call<DadJoke> call, Response<DadJoke> response) {
                dataTarget.setValue(response.body().joke);
            }

            @Override
            public void onFailure(Call<DadJoke> call, Throwable t) {
                dataTarget.setValue(t.getLocalizedMessage());
            }
        });
    }
}


