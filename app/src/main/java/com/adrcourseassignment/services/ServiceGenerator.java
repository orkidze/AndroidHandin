package com.adrcourseassignment.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ServiceGenerator {
    private static GeekJokesAPI geekJokesAPI;
    private static DadJokesAPI dadJokesAPI;

    public static GeekJokesAPI getGeekJokesApi() {
        if(geekJokesAPI == null)
            initGeekJokesApi();
        return geekJokesAPI;
    }

    public static DadJokesAPI getDadJokesAPI() {
        if(dadJokesAPI == null)
            initDadJokesApi();
        return dadJokesAPI;
    }
    private static void initDadJokesApi() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit.Builder dadJokesRetrofit = new Retrofit.Builder()
                .baseUrl("https://icanhazdadjoke.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create());

        Retrofit dadJokesRet = dadJokesRetrofit.build();

        dadJokesAPI = dadJokesRet.create(DadJokesAPI.class);
    }
    private static void initGeekJokesApi() {
        Retrofit.Builder GeekJokesRetrofitBuilder = new Retrofit.Builder()
                .baseUrl("https://geek-jokes.sameerkumar.website")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit GeekJokesRetrofit = GeekJokesRetrofitBuilder.build();

        geekJokesAPI = GeekJokesRetrofit.create(GeekJokesAPI.class);
    }

}
