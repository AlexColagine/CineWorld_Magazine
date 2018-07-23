package com.alex.android.cineworld.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CineWorldApi {

    public static final String MOVIE_URL = "https://api.themoviedb.org";

    public static EndPoint getRequest(){
        return new Retrofit.Builder()
                .baseUrl(MOVIE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EndPoint.class);
    }

}
