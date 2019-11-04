package com.adrcourseassignment.services;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GeekJokesAPI {
    @GET("/api")
    Call<String> getJoke();

}
