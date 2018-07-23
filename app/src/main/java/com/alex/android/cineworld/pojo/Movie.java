package com.alex.android.cineworld.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.alex.android.cineworld.utils.Utils.BACKDROP_IMAGE;
import static com.alex.android.cineworld.utils.Utils.ID_MOVIE;
import static com.alex.android.cineworld.utils.Utils.MOVIE;
import static com.alex.android.cineworld.utils.Utils.OVERVIEW;
import static com.alex.android.cineworld.utils.Utils.POSTER_PATH;
import static com.alex.android.cineworld.utils.Utils.RELEASE_DATE;
import static com.alex.android.cineworld.utils.Utils.TITLE;
import static com.alex.android.cineworld.utils.Utils.VOTE_AVERAGE;

/**
 * Created by Alessandro on 18/02/2018.
 */

@Entity(tableName = MOVIE)
public class Movie implements Parcelable {

    @ColumnInfo(name = ID_MOVIE)
    @SerializedName(ID_MOVIE)
    private int id;

    @ColumnInfo(name = TITLE)
    @SerializedName(TITLE)
    private String title;

    @ColumnInfo(name = POSTER_PATH)
    @SerializedName(POSTER_PATH)
    private String image;

    @ColumnInfo(name = OVERVIEW)
    @SerializedName(OVERVIEW)
    private String plot;

    @ColumnInfo(name = VOTE_AVERAGE)
    @SerializedName(VOTE_AVERAGE)
    private double rating;

    @ColumnInfo(name = RELEASE_DATE)
    @SerializedName(RELEASE_DATE)
    private String date;

    @ColumnInfo(name = BACKDROP_IMAGE)
    @SerializedName(BACKDROP_IMAGE)
    private String backdrop_image;

    private boolean favorite;

    public Movie(int id, String title, String image, String plot, double rating, String date, String backdrop_image, boolean favorite) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.plot = plot;
        this.rating = rating;
        this.date = date;
        this.backdrop_image = backdrop_image;
        this.favorite = favorite;
    }

    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getImage(){
        return image;
    }

    public String getPlot(){
        return plot;
    }

    public double getRating(){
        return rating;
    }

    public String getDate(){
        return date;
    }

    public String getBackdrop(){
        return backdrop_image;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.image);
        dest.writeString(this.plot);
        dest.writeDouble(this.rating);
        dest.writeString(this.date);
        dest.writeString(this.backdrop_image);
        dest.writeByte(this.favorite ? (byte) 1 : (byte) 0);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.image = in.readString();
        this.plot = in.readString();
        this.rating = in.readDouble();
        this.date = in.readString();
        this.backdrop_image = in.readString();
        this.favorite = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
