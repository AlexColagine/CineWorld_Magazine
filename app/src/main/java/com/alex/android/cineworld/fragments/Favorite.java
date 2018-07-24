package com.alex.android.cineworld.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.alex.android.cineworld.R;
import com.alex.android.cineworld.adapters.FavoriteAdapter;



/**
 * A simple {@link Fragment} subclass.
 */
public class Favorite extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PASSWORD_LOADER = 0;
    FavoriteAdapter mCursorFavoriteAdapter;
    RecyclerView favoriteRecycler;
    View emptyView;
    View rootView;

    public Favorite() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_favorite, container, false);

        favoriteRecycler = (RecyclerView) rootView.findViewById(R.id.recycler);
        emptyView = rootView.findViewById(R.id.empty_view);
        favoriteRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteRecycler.setHasFixedSize(true);
        mCursorFavoriteAdapter = new FavoriteAdapter(getContext(), null);
        favoriteRecycler.setAdapter(mCursorFavoriteAdapter);

        //getLoaderManager().initLoader(PASSWORD_LOADER, null, this);
        setHasOptionsMenu(true);
        return rootView;
    }

   @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
       /* String[] projection = {
                MovieContract.MovieEntry.COLUMN_MOVIE_ID,
                MovieContract.MovieEntry.COLUMN_TITLE,
                MovieContract.MovieEntry.COLUMN_DATE,
                MovieContract.MovieEntry.COLUMN_RATING,
                MovieContract.MovieEntry.COLUMN_PLOT,
                MovieContract.MovieEntry.COLUMN_IMAGE_POSTER,
                MovieContract.MovieEntry.COLUMN_IMAGE_BACKDROP,};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(getContext(),   // Parent activity context
                MovieContract.MovieEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);             // Default sort order  */
       return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (!data.moveToFirst()) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
        mCursorFavoriteAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorFavoriteAdapter.swapCursor(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item_share = menu.findItem(R.id.action_share);
        item_share.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
