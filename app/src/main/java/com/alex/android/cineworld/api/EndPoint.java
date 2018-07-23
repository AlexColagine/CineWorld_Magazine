package com.alex.android.cineworld.api;

import com.alex.android.cineworld.pojo.Movie;
import com.alex.android.cineworld.pojo.Review;
import com.alex.android.cineworld.pojo.ServerResponse;
import com.alex.android.cineworld.pojo.Video;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EndPoint {

    @GET("/3/movie/popular")
    Call<ServerResponse> getPopularMovies(@Query("api_key") String apiKey);

    //@GET("/3/movie/popular")
    //Call<ArrayList<ServerResponse>> getPopularMovies(@Query("api_key") String apiKey);

    @GET("/3/movie/top_rated")
    Call<ArrayList<ServerResponse>> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("/3/movie/upcoming")
    Call<ArrayList<ServerResponse>> getUpcomingMovies(@Query("api_key") String apiKey);

    @GET("/3/movie/now_playing")
    Call<ArrayList<ServerResponse>> getNowPlayingMovies(@Query("api_key") String apiKey);

    @GET("/3/movie/{id}/videos")
    Call<ArrayList<Video>> getVideo (@Path("id") int id , @Query("api_key") String apiKey);

    @GET("/3/movie/{id}/reviews")
    Call<ArrayList<Review>> getReview (@Path("id") int id , @Query("api_key") String apiKey);

    @GET("/3/movie?query={text}")
    Call<ArrayList<Movie>> getSearchMovie (@Path("text") String searchText , @Query("api_key") String apiKey);
}
