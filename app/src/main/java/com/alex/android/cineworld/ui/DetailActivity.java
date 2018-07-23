package com.alex.android.cineworld.ui;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.android.cineworld.adapters.ReviewAdapter;
import com.alex.android.cineworld.adapters.VideoAdapter;
import com.alex.android.cineworld.api.CineWorldApi;
import com.alex.android.cineworld.api.EndPoint;
import com.alex.android.cineworld.pojo.Movie;
import com.alex.android.cineworld.pojo.Review;
import com.alex.android.cineworld.pojo.Video;
import com.example.android.cineworld.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alex.android.cineworld.utils.Utils.API_KEY;
import static com.alex.android.cineworld.utils.Utils.IMAGE_SIZE;
import static com.alex.android.cineworld.utils.Utils.IMAGE_URL;
import static com.alex.android.cineworld.utils.Utils.MOVIE;


/**
 * Created by Alessandro on 19/02/2018.
 */

public class DetailActivity extends AppCompatActivity {

    private Movie movieList;
    private ReviewAdapter reviewAdapter;
    private VideoAdapter videoAdapter;
    private ArrayList<Video> videoArrayList = new ArrayList<>();
    private ArrayList<Review> reviewArrayList = new ArrayList<>();
    private RecyclerView reviewRecycler;
    private RecyclerView videoRecycler;
    private TextView video;
    private TextView review;
    private FloatingActionButton fab;
    private EndPoint endPointRequest = CineWorldApi.getRequest();

    public static final String RECYCLER_VIDEO_STATE = "recycler_video";
    Parcelable videoListState;
    public static final String RECYCLER_REVIEW_STATE = "recycler_state";
    Parcelable reviewListState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        video = findViewById(R.id.video_tv);
        review = findViewById(R.id.review_tv);
        fab = findViewById(R.id.fab_favorite);
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        /**
         *  Get object Movie position
         */
        Bundle data = getIntent().getExtras();

        if (data != null) {
            movieList = data.getParcelable(MOVIE);
        }
        int id = movieList != null ? movieList.getId() : 0;

        getVideoAPI(id);
        getReviewAPI(id);

        updateInfoUi();
        updateVideoUI();
        updateReviewUi();
        updateFabUI();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!movieList.getFavorite()) {
                    insertFavorite();
                    fab.setImageDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.icon_fill_fab));
                    movieList.setFavorite(true);
                } else {
                    deleteAllMovies();
                    fab.setImageDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.icon_fab));
                    movieList.setFavorite(false);
                }
            }
        });

        if (savedInstanceState != null) {
            reviewListState = savedInstanceState.getParcelable(RECYCLER_REVIEW_STATE);
            videoListState = savedInstanceState.getParcelable(RECYCLER_VIDEO_STATE);
        }

    }

    /**
     * It used for update the UI of the Detail Movie
     */
    @SuppressLint("SetTextI18n")
    private void updateInfoUi() {
        //noinspection ConstantConditions
        getSupportActionBar().setTitle(movieList.getTitle());

        ImageView imageBackdrop = findViewById(R.id.image_backdrop);
        Picasso.with(this)
                .load(IMAGE_URL
                        .concat(IMAGE_SIZE[5])
                        .concat(movieList.getBackdrop()))
                .placeholder(R.drawable.icon_movie)
                .error(R.drawable.icon_error)
                .into(imageBackdrop);

        ImageView poster = findViewById(R.id.poster_detail);
        Picasso.with(this)
                .load(IMAGE_URL
                        .concat(IMAGE_SIZE[5])
                        .concat(movieList.getImage()))
                .placeholder(R.drawable.icon_movie)
                .error(R.drawable.icon_error)
                .into(poster);

        TextView date = findViewById(R.id.date_tv);
        date.setText(movieList.getDate());

        TextView rating = findViewById(R.id.rating_tv);
        rating.setText(String.valueOf(movieList.getRating()) + "\n" + "/10");

        TextView plot = findViewById(R.id.plot_tv);
        plot.setText(movieList.getPlot());
    }

    /**
     * It used for update the UI of the Review Movie
     */
    private void updateReviewUi() {
        reviewRecycler = findViewById(R.id.recyclerReview);
        reviewRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        reviewRecycler.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        reviewRecycler.setHasFixedSize(true);
        reviewAdapter = new ReviewAdapter(getApplicationContext(), reviewArrayList);
        reviewRecycler.setAdapter(reviewAdapter);
    }

    /**
     * It used for update the UI of the Video Movie
     */
    private void updateVideoUI() {
        videoRecycler = findViewById(R.id.recyclerVideo);
        videoRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        videoRecycler.setHasFixedSize(true);
        videoAdapter = new VideoAdapter(getApplicationContext(), videoArrayList);
        videoRecycler.setAdapter(videoAdapter);
    }

    /**
     * It ued for update the drawable of the Floating Button
     */
    private void updateFabUI() {
        if (!movieList.getFavorite()) {
            fab.setImageDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.icon_fab));
        } else {
            fab.setImageDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.icon_fill_fab));
        }
    }

    private void getVideoAPI(int movieID) {
        Call<ArrayList<Video>> response = endPointRequest.getVideo(movieID, API_KEY);

        response.enqueue(new Callback<ArrayList<Video>>() {
            @Override
            public void onResponse(Call<ArrayList<Video>> call, Response<ArrayList<Video>> response) {
                if (response.isSuccessful()) {
                    videoArrayList.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Video>> call, Throwable t) {

            }
        });
    }

    private void getReviewAPI(int movieID) {
        Call<ArrayList<Review>> response = endPointRequest.getReview(movieID, API_KEY);

        response.enqueue(new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                if (response.isSuccessful()) {
                    reviewArrayList.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Review>> call, Throwable t) {

            }
        });
    }

    /**
     * Add a Movie in Database --> Fragment --> Favorite
     */
    private void insertFavorite() {
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movieList.getId());
        values.put(MovieContract.MovieEntry.COLUMN_TITLE, movieList.getTitle());
        values.put(MovieContract.MovieEntry.COLUMN_RATING, movieList.getRating());
        values.put(MovieContract.MovieEntry.COLUMN_DATE, movieList.getDate());
        values.put(MovieContract.MovieEntry.COLUMN_IMAGE_POSTER, movieList.getImage());
        values.put(MovieContract.MovieEntry.COLUMN_IMAGE_BACKDROP, movieList.getBackdrop());
        values.put(MovieContract.MovieEntry.COLUMN_PLOT, movieList.getPlot());
        this.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);
        Toast.makeText(DetailActivity.this, getString(R.string.favorite_insert), Toast.LENGTH_SHORT).show();
    }

    /**
     * Delete a single movie with univocal ID
     */
    private void deleteAllMovies() {
        int rowsDeleted = this.getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI,
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = " + movieList.getId(), null);
        Toast.makeText(DetailActivity.this, getString(R.string.favorite_delete), Toast.LENGTH_SHORT).show();
        Log.v("DetailActivity", rowsDeleted + " rows deleted from movie database");
    }

    /**
     * Share through another app the Detail of the Movie
     */
    private void share_movie() {
        String title = movieList.getTitle();
        String date = movieList.getDate();
        String plot = movieList.getPlot();
        double rating = movieList.getRating();

        String textToShare = "Send from PopularMovies" + "\n" + "\n" + "\n" +
                "Title: " + title + "\n" +
                "Rating: " + rating + "\n" +
                "Date: " + date + "\n" +
                "Plot: " + plot;

        ShareCompat.IntentBuilder
                .from(this)
                .setType("text/plain")
                .setText(textToShare)
                .setChooserTitle(getResources().getText(R.string.share_text))
                .startChooser();
    }

    private void downloadImage(String url) {

        File direct = new File(Environment.getExternalStorageDirectory()
                + "/dhaval_files");

        if (!direct.exists()) {
            //noinspection ResultOfMethodCallIgnored
            direct.mkdirs();
        }

        DownloadManager mgr = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);

        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle("Demo")
                .setDescription("Something useful. No, really.")
                .setDestinationInExternalPublicDir("/" + getString(R.string.app_name), "test.jpg");

        if (mgr != null) {
            mgr.enqueue(request);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item_share = menu.findItem(R.id.action_share);
        item_share.setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                share_movie();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(DetailActivity.this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the scrolling on RecyclerView (Video and Review) when the orientation change.
        if (reviewRecycler != null) {
            outState.putParcelable(RECYCLER_REVIEW_STATE, reviewRecycler.getLayoutManager().onSaveInstanceState());
        }
        if (videoRecycler != null) {
            outState.putParcelable(RECYCLER_VIDEO_STATE, videoRecycler.getLayoutManager().onSaveInstanceState());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
