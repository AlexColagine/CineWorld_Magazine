package com.alex.android.cineworld.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.android.cineworld.R;
import com.alex.android.cineworld.api.CineWorldApi;
import com.alex.android.cineworld.api.EndPoint;
import com.alex.android.cineworld.fragments.Favorite;
import com.alex.android.cineworld.fragments.MovieFragment;
import com.alex.android.cineworld.pojo.Movie;
import com.alex.android.cineworld.pojo.ServerResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alex.android.cineworld.utils.Utils.API_KEY;


public class MainActivity extends AppCompatActivity {

    public TabLayout tabLayout;
    public TextView tabMost,
            tabTop,
            tabNow,
            tabUp,
            tabFav;
    private int[] imageResId = {
            R.drawable.most_popular_image, //0
            R.drawable.top_rated_image,    //1
            R.drawable.nowplaying_image,   //2
            R.drawable.upcoming_image,     //3
            R.drawable.favorite_image,     //4
            R.drawable.most_popular_image_yellow, //5
            R.drawable.top_rated_image_yellow,    //6
            R.drawable.nowplaying_image_yellow,   //7
            R.drawable.upcoming_image_yellow,     //8
            R.drawable.favorite_image_yellow      //9
    };
    public ArrayList<Movie> movieArrayList;
    public String failureResponse;
    EndPoint endPointRequest = CineWorldApi.getRequest();
    MovieFragment movieFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager mViewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint("NewApi")
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        getPopularAPI();
                        updateUiMostPopular();
                        break;
                    case 1:
                        getTopAPI();
                        updateUiTopRated();
                        break;
                    case 2:
                        getPlayingAPI();
                        updateUiNowPlaying();
                        break;
                    case 3:
                        getUpComingAPI();
                        updateUiUpComing();
                        break;
                    case 4:
                        updateUiFavorite();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        movieFragment = MovieFragment.newInstance(movieArrayList);
        //MovieFragment movieTopFragment = MovieFragment.newInstance(movieArrayList);
        //
        MoviePagerAdapter mMoviePagerAdapter = new MoviePagerAdapter(getSupportFragmentManager(),
                movieFragment
                /*movieTopFragment*/);
        mViewPager.setAdapter(mMoviePagerAdapter);
        setupTabIcons();

    }

    public void getPopularAPI() {
        Call<ServerResponse> response = endPointRequest.getPopularMovies(API_KEY);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse = response.body();
                if (serverResponse != null) {
                    movieArrayList = serverResponse.getResults();
                    //movieArrayList.addAll(serverResponse.getResults());
                    Toast.makeText(MainActivity.this, movieArrayList.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                failureResponse = getString(R.string.movies_no_found);
                Toast.makeText(MainActivity.this, failureResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTopAPI() {
        Call<ArrayList<ServerResponse>> response = endPointRequest.getTopRatedMovies(API_KEY);

        response.enqueue(new Callback<ArrayList<ServerResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ServerResponse>> call, Response<ArrayList<ServerResponse>> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<ArrayList<ServerResponse>> call, Throwable t) {
                failureResponse = getString(R.string.movies_no_found);
                Toast.makeText(MainActivity.this, failureResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPlayingAPI() {
        Call<ArrayList<ServerResponse>> response = endPointRequest.getNowPlayingMovies(API_KEY);

        response.enqueue(new Callback<ArrayList<ServerResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ServerResponse>> call, Response<ArrayList<ServerResponse>> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<ArrayList<ServerResponse>> call, Throwable t) {
                failureResponse = getString(R.string.movies_no_found);
                Toast.makeText(MainActivity.this, failureResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUpComingAPI() {
        Call<ArrayList<ServerResponse>> response = endPointRequest.getUpcomingMovies(API_KEY);

        response.enqueue(new Callback<ArrayList<ServerResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ServerResponse>> call, Response<ArrayList<ServerResponse>> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<ArrayList<ServerResponse>> call, Throwable t) {
                failureResponse = getString(R.string.movies_no_found);
                Toast.makeText(MainActivity.this, failureResponse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * METHODS :
     * <p>
     * 1 - updateUiMostPopular()
     * 2 - updateUiTopRated()
     * 3 - updateUiNowPlaying()
     * 4 - updateUiUpComing()
     * 5 - updateUiFavorite()
     * 6 - setupTabIcons()
     * <p>
     * They are used for the UI of the TabLayout. In particular to set a text of each Fragment with specific icon drawable.
     * They can be useful to change color of a single drawable when ViewPager scroll.
     */

    @SuppressLint("NewApi")
    private void updateUiMostPopular() {
        tabMost.setCompoundDrawablesWithIntrinsicBounds(imageResId[5], 0, 0, 0);
        tabTop.setCompoundDrawablesWithIntrinsicBounds(imageResId[1], 0, 0, 0);
        tabNow.setCompoundDrawablesWithIntrinsicBounds(imageResId[2], 0, 0, 0);
        tabUp.setCompoundDrawablesWithIntrinsicBounds(imageResId[3], 0, 0, 0);
        tabFav.setCompoundDrawablesWithIntrinsicBounds(imageResId[4], 0, 0, 0);

        tabMost.setTextColor(Color.WHITE);
        tabTop.setTextColor(getColor(R.color.colorToolbar));
        tabNow.setTextColor(getColor(R.color.colorToolbar));
        tabUp.setTextColor(getColor(R.color.colorToolbar));
        tabFav.setTextColor(getColor(R.color.colorToolbar));
    }

    @SuppressLint("NewApi")
    private void updateUiTopRated() {
        tabMost.setCompoundDrawablesWithIntrinsicBounds(imageResId[0], 0, 0, 0);
        tabTop.setCompoundDrawablesWithIntrinsicBounds(imageResId[6], 0, 0, 0);
        tabNow.setCompoundDrawablesWithIntrinsicBounds(imageResId[2], 0, 0, 0);
        tabUp.setCompoundDrawablesWithIntrinsicBounds(imageResId[3], 0, 0, 0);
        tabFav.setCompoundDrawablesWithIntrinsicBounds(imageResId[4], 0, 0, 0);

        tabMost.setTextColor(getColor(R.color.colorToolbar));
        tabTop.setTextColor(Color.WHITE);
        tabNow.setTextColor(getColor(R.color.colorToolbar));
        tabUp.setTextColor(getColor(R.color.colorToolbar));
        tabFav.setTextColor(getColor(R.color.colorToolbar));
    }

    @SuppressLint("NewApi")
    private void updateUiNowPlaying() {
        tabMost.setCompoundDrawablesWithIntrinsicBounds(imageResId[0], 0, 0, 0);
        tabTop.setCompoundDrawablesWithIntrinsicBounds(imageResId[1], 0, 0, 0);
        tabNow.setCompoundDrawablesWithIntrinsicBounds(imageResId[7], 0, 0, 0);
        tabUp.setCompoundDrawablesWithIntrinsicBounds(imageResId[3], 0, 0, 0);
        tabFav.setCompoundDrawablesWithIntrinsicBounds(imageResId[4], 0, 0, 0);

        tabMost.setTextColor(getColor(R.color.colorToolbar));
        tabTop.setTextColor(getColor(R.color.colorToolbar));
        tabNow.setTextColor(Color.WHITE);
        tabUp.setTextColor(getColor(R.color.colorToolbar));
        tabFav.setTextColor(getColor(R.color.colorToolbar));
    }

    @SuppressLint("NewApi")
    private void updateUiUpComing() {
        tabMost.setCompoundDrawablesWithIntrinsicBounds(imageResId[0], 0, 0, 0);
        tabTop.setCompoundDrawablesWithIntrinsicBounds(imageResId[1], 0, 0, 0);
        tabNow.setCompoundDrawablesWithIntrinsicBounds(imageResId[2], 0, 0, 0);
        tabUp.setCompoundDrawablesWithIntrinsicBounds(imageResId[8], 0, 0, 0);
        tabFav.setCompoundDrawablesWithIntrinsicBounds(imageResId[4], 0, 0, 0);

        tabMost.setTextColor(getColor(R.color.colorToolbar));
        tabTop.setTextColor(getColor(R.color.colorToolbar));
        tabNow.setTextColor(getColor(R.color.colorToolbar));
        tabUp.setTextColor(Color.WHITE);
        tabFav.setTextColor(getColor(R.color.colorToolbar));
    }

    @SuppressLint("NewApi")
    private void updateUiFavorite() {
        tabMost.setCompoundDrawablesWithIntrinsicBounds(imageResId[0], 0, 0, 0);
        tabTop.setCompoundDrawablesWithIntrinsicBounds(imageResId[1], 0, 0, 0);
        tabNow.setCompoundDrawablesWithIntrinsicBounds(imageResId[2], 0, 0, 0);
        tabUp.setCompoundDrawablesWithIntrinsicBounds(imageResId[3], 0, 0, 0);
        tabFav.setCompoundDrawablesWithIntrinsicBounds(imageResId[9], 0, 0, 0);

        tabMost.setTextColor(getColor(R.color.colorToolbar));
        tabTop.setTextColor(getColor(R.color.colorToolbar));
        tabNow.setTextColor(getColor(R.color.colorToolbar));
        tabUp.setTextColor(getColor(R.color.colorToolbar));
        tabFav.setTextColor(Color.WHITE);
    }

    @SuppressWarnings("ConstantConditions")
    @SuppressLint({"InflateParams", "NewApi"})
    private void setupTabIcons() {

        tabMost = (TextView) LayoutInflater.from(this).inflate(R.layout.name_fragment, null);
        tabMost.setText(R.string.most_popular);
        tabMost.setTextColor(Color.WHITE);
        tabMost.setCompoundDrawablesWithIntrinsicBounds(imageResId[5], 0, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabMost);

        tabTop = (TextView) LayoutInflater.from(this).inflate(R.layout.name_fragment, null);
        tabTop.setText(R.string.top_rated);
        tabTop.setCompoundDrawablesWithIntrinsicBounds(imageResId[1], 0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTop);

        tabNow = (TextView) LayoutInflater.from(this).inflate(R.layout.name_fragment, null);
        tabNow.setText(R.string.now_playing);
        tabNow.setCompoundDrawablesWithIntrinsicBounds(imageResId[2], 0, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabNow);

        tabUp = (TextView) LayoutInflater.from(this).inflate(R.layout.name_fragment, null);
        tabUp.setText(R.string.upcoming);
        tabUp.setCompoundDrawablesWithIntrinsicBounds(imageResId[3], 0, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabUp);

        tabFav = (TextView) LayoutInflater.from(this).inflate(R.layout.name_fragment, null);
        tabFav.setText(R.string.favorite);
        tabFav.setCompoundDrawablesWithIntrinsicBounds(imageResId[4], 0, 0, 0);
        tabLayout.getTabAt(4).setCustomView(tabFav);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item_share = menu.findItem(R.id.action_share);
        item_share.setVisible(false);
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return null;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class MoviePagerAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 5;
        private MovieFragment movieFragment;
        //private MovieFragment movieTopFragment;

        MoviePagerAdapter(FragmentManager fm,
                          MovieFragment movieFragment /*MovieFragment movieTopFragment*/) {
            super(fm);
            this.movieFragment = movieFragment;
            //this.movieTopFragment = movieTopFragment;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MovieFragment();
                case 1:
                    return movieFragment;
                case 2:
                    return movieFragment;
                case 3:
                    return movieFragment;
                case 4:
                    return new Favorite();
            }
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            // return PlaceholderFragment.newInstance(position + 1);
            return null;
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return PAGE_COUNT;
        }
    }

}
