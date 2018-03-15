package com.example.android.popularmovies.Loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.popularmovies.Object.Movie;
import com.example.android.popularmovies.Utils.QueryUtils;

import java.util.List;

/**
 * Created by Alessandro on 18/02/2018.
 */

//carica i dati presi dall'url in un thread in background...
public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    private String mUrl;

    public MovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


    @Override
    public List<Movie> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        return QueryUtils.fetchNewsDataMovie(mUrl);

    }

}
