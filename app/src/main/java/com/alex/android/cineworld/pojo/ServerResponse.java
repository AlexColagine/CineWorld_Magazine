package com.alex.android.cineworld.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import static com.alex.android.cineworld.utils.Utils.RESULTS;

public class ServerResponse {

    @SerializedName(RESULTS)
    private ArrayList<Movie> results;

    public ArrayList<Movie> getResults() {
        return results;
    }

    public void setResults(ArrayList<Movie> results){
        this.results = results;
    }
}
