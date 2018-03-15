package com.example.android.popularmovies.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.Adapters.MovieAdapter;
import com.example.android.popularmovies.Loaders.MovieLoader;
import com.example.android.popularmovies.Object.Movie;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.UI.DetailActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.popularmovies.Utils.Utils.MOVIE;
import static com.example.android.popularmovies.Utils.Utils.QUERY_MOVIE;
import static com.example.android.popularmovies.Utils.Utils.buildUrlMovie;
import static com.example.android.popularmovies.Utils.Utils.calculateNoOfColumns;

public class PopularRated extends Fragment implements LoaderManager.LoaderCallbacks<List<Movie>>, MovieAdapter.ListItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private MovieAdapter mAdapter;
    View mEmptyView;
    TextView mEmptyTextView;
    View rootView;
    View loadingIndicator;
    private static int LOADER_ID = 1;
    SwipeRefreshLayout swipe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_popular_rated, container, false);
        swipe = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        swipe.setOnRefreshListener(this);

        RecyclerView movieRecycler = (RecyclerView) rootView.findViewById(R.id.recycler);
        mEmptyView = rootView.findViewById(R.id.empty_view);
        mEmptyTextView = (TextView) rootView.findViewById(R.id.empty_text);
        movieRecycler.setLayoutManager(new GridLayoutManager(getContext(), calculateNoOfColumns(getContext())));
        movieRecycler.setHasFixedSize(true);
        mAdapter = new MovieAdapter(getContext(), new ArrayList<Movie>(), this);
        movieRecycler.setAdapter(mAdapter);

        loadingIndicator = rootView.findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.VISIBLE);

        Connectivity();
        getLoaderManager().initLoader(LOADER_ID, null, this);

        return rootView;

    }

    /**
     * It used to verify if there is a connection (WiFi or data mobile)
     */
    public void Connectivity() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            getLoaderManager().initLoader(LOADER_ID, null, this);
        } else {
            loadingIndicator.setVisibility(View.GONE);
            mEmptyTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        return new MovieLoader(getContext(), buildUrlMovie(QUERY_MOVIE));
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movieList) {
        swipe.setRefreshing(false);
        loadingIndicator.setVisibility(View.GONE);
        mEmptyTextView.setText(R.string.movies_no_found);

        mAdapter.movieList.clear();

        if (movieList != null && !movieList.isEmpty()) {
            mEmptyView.setVisibility(View.GONE);
            mAdapter.movieList.addAll(movieList);
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        mAdapter.movieList.clear();
    }

    @Override
    public void onRefresh() {
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public void onListItemClick(int position) {
        Movie moviePosition = mAdapter.getItem(position);
        Intent sendDetail = new Intent(getContext(), DetailActivity.class);
        sendDetail.putExtra(MOVIE, moviePosition);
        startActivity(sendDetail);
    }
}
