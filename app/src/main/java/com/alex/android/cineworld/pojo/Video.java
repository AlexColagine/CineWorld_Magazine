package com.alex.android.cineworld.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.alex.android.cineworld.utils.Utils.NAME_VIDEO;
import static com.alex.android.cineworld.utils.Utils.SIZE_VIDEO;
import static com.alex.android.cineworld.utils.Utils.SOURCE_VIDEO;
import static com.alex.android.cineworld.utils.Utils.TYPE_VIDEO;

/**
 * Created by Alessandro on 15/03/2018.
 */

public class Video implements Parcelable {

    @SerializedName(NAME_VIDEO)
    private String name;

    @SerializedName(SIZE_VIDEO)
    private String size;

    @SerializedName(SOURCE_VIDEO)
    private String source;

    @SerializedName(TYPE_VIDEO)
    private String type;

    public Video(String name, String size, String source, String type) {
        this.name = name;
        this.size = size;
        this.source = source;
        this.type = type;
    }



    public String getName(){
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getSource() {
        return source;
    }

    public String getType(){
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.size);
        dest.writeString(this.source);
        dest.writeString(this.type);
    }

    protected Video(Parcel in) {
        this.name = in.readString();
        this.size = in.readString();
        this.source = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
}
