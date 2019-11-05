package com.adrcourseassignment.services.jokes;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GeekJokesAPI {
    @GET("/api")
    Call<String> getJoke();

}
