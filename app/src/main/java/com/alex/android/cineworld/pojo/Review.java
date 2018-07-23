package com.alex.android.cineworld.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.alex.android.cineworld.utils.Utils.AUTHOR_REVIEW;
import static com.alex.android.cineworld.utils.Utils.CONTENT_REVIEW;
import static com.alex.android.cineworld.utils.Utils.ID_REVIEW;
import static com.alex.android.cineworld.utils.Utils.URL_REVIEW;

/**
 * Created by Alessandro on 15/03/2018.
 */

public class Review implements Parcelable {

    @SerializedName(ID_REVIEW)
    private String id;

    @SerializedName(AUTHOR_REVIEW)
    private String author;

    @SerializedName(CONTENT_REVIEW)
    private String content;

    @SerializedName(URL_REVIEW)
    private String url;

    public Review ( String id , String author , String content , String url){
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getId(){
        return id;
    }

    public String getAuthor(){
        return author;
    }

    public String getContent(){
        return content;
    }

    public String getUrl(){
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.author);
        dest.writeString(this.content);
        dest.writeString(this.url);
    }

    protected Review(Parcel in) {
        this.id = in.readString();
        this.author = in.readString();
        this.content = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel source) {
            return new Review(source);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}
