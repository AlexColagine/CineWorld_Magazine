package com.alex.android.cineworld.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alex.android.cineworld.R;
import com.alex.android.cineworld.adapters.MovieAdapter;
import com.alex.android.cineworld.pojo.Movie;
import com.alex.android.cineworld.ui.DetailActivity;

import java.util.ArrayList;

import static com.alex.android.cineworld.utils.Utils.MOVIE;
import static com.alex.android.cineworld.utils.Utils.MOVIE_ARRAYLIST;
import static com.alex.android.cineworld.utils.Utils.calculateNoOfColumns;


public class MovieFragment extends Fragment implements MovieAdapter.ListItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    MovieAdapter mAdapter;
    ArrayList<Movie> movieArrayList;
    private View mEmptyView;
    private TextView mEmptyTextView;
    private View loadingIndicator;
    private SwipeRefreshLayout swipe;

    public static MovieFragment newInstance(ArrayList<Movie> movieArrayList) {
        MovieFragment movieFragment = new MovieFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(MOVIE_ARRAYLIST, movieArrayList);
        movieFragment.setArguments(bundle);

        return movieFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);

        loadingIndicator = rootView.findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.VISIBLE);
        swipe = rootView.findViewById(R.id.swiperefresh);
        swipe.setOnRefreshListener(this);

        RecyclerView movieRecycler = rootView.findViewById(R.id.recycler);
        mEmptyView = rootView.findViewById(R.id.empty_view);
        mEmptyTextView = rootView.findViewById(R.id.empty_text);
        //noinspection ConstantConditions
        movieRecycler.setLayoutManager(new GridLayoutManager(getContext(), calculateNoOfColumns(getContext())));
        movieRecycler.setHasFixedSize(true);

        if (getArguments() != null) {
            movieArrayList = getArguments().getParcelableArrayList(MOVIE_ARRAYLIST);
            mEmptyView.setVisibility(View.GONE);
        } else{
            swipe.setRefreshing(false);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyTextView.setText(R.string.movies_no_found);
        }
        mAdapter = new MovieAdapter(getContext(), movieArrayList, this);
        movieRecycler.setAdapter(mAdapter);

        return rootView;

    }

    @Override
    public void onRefresh() {

    }

    //cambiare la KeyString per il movieArrayList e lasciare la MOVIE così com'è ...
    @Override
    public void onListItemClick(int position) {
        Movie moviePosition = mAdapter.getItem(position);
        Intent sendDetail = new Intent(getContext(), DetailActivity.class);
        sendDetail.putExtra(MOVIE, moviePosition);
        startActivity(sendDetail);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item_share = menu.findItem(R.id.action_share);
        item_share.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
