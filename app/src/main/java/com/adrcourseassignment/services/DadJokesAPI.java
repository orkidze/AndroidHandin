package com.adrcourseassignment.services;

import com.adrcourseassignment.dataModels.DadJoke;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface DadJokesAPI {
    @Headers("Accept: application/json")
    @GET("/")
    Call<DadJoke> getJoke();
}
