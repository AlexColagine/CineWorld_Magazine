package com.alex.android.cineworld.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.example.android.cineworld.BuildConfig;

/**
 * Created by Alessandro on 02/03/2018.
 */

public class Utils extends AppCompatActivity {

    /**
     * Strings to create URL in buildUrlMovie method.
     * * @API_KEY
     * ** @TMDB_MOVIE_URL
     * *** @QUERY_PARAM ---> are unique for all url method (Movie , Review , Video)
     */
    public static final String TMDB_MOVIE_URL = "https://api.themoviedb.org/3/movie/";
    public static final String QUERY_PARAM = "api_key";
    public static final String API_KEY = BuildConfig.API_KEY;
    public static final String IMAGE_URL = "http://image.tmdb.org/t/p/";

    /**
     * Vector with different size of ImageView
     */
    public static final String[] IMAGE_SIZE = {"w92", "w154", "w185", "w342", "w500", "w780", "original"};

    /**
     * Strings to create URL in buildUrlReviewVideo method.
     */
    public static String QUERY_VIDEO_REVIEW = "";
    public static int QUERY_ID_PATH;
    public static String QUERY_REVIEW_PATH = "reviews";
    public static String QUERY_VIDEO_PATH = "trailers";

    /**
     * Strings for the JSON Movie results
     */
    public static final String ID_MOVIE = "id";
    public static final String TITLE = "title";
    public static final String POSTER_PATH = "poster_path";
    public static final String OVERVIEW = "overview";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String RELEASE_DATE = "release_date";
    public static final String BACKDROP_IMAGE = "backdrop_path";

    /**
     * String for the JSON Review results
     */
    public static final String ID_REVIEW = "id";
    public static final String AUTHOR_REVIEW = "author";
    public static final String CONTENT_REVIEW = "content";
    public static final String URL_REVIEW = "url";

    /**
     * Pojo ServerResponse
     */
    public static final String RESULTS = "results";

    /**
     * String for the JSON Video results
     */
    public static final String YOUTUBE_OBJECT = "youtube";
    public static final String NAME_VIDEO = "name";
    public static final String SIZE_VIDEO = "size";
    public static final String SOURCE_VIDEO = "source";
    public static final String TYPE_VIDEO = "type";

    public static final String URL_IMAGE_YOUTUBE = "https://img.youtube.com/vi/";
    public static final String SIZE_IMAGE_YOUTUBE = "maxresdefault.jpg";
    public static final String INTENT_YOUTUBE_APP = "vnd.youtube:";
    public static final String WEB_INTENT_YOUTUBE = "http://www.youtube.com/watch?v=";

    /**
     * String/key for the parcelable object used in MovieFragment , DetailActivity and Favorite.
     */
    public static final String MOVIE = "Movie";
    public static final String MOVIE_ARRAYLIST = "movie_array_list";

    /**
     * @param context
     * @return an integer value to show a columns of the GridLayout in RecyclerView
     */
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }

    /**
     * @param progress is used to set a value of brightness
     * @return progress param
     */
    public static PorterDuffColorFilter applyLightness(int progress) {
        if (progress > 0) {
            int value = progress * 255 / 100;
            return new PorterDuffColorFilter(Color.argb(value, 255, 255, 255), PorterDuff.Mode.SRC_OVER);
        } else {
            int value = (progress * -1) * 255 / 100;
            return new PorterDuffColorFilter(Color.argb(value, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);
        }
    }

}
