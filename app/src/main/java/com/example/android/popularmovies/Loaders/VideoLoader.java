package com.example.android.popularmovies.Loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.popularmovies.Object.Video;
import com.example.android.popularmovies.Utils.QueryUtils;

import java.util.List;

/**
 * Created by Alessandro on 15/03/2018.
 */

public class VideoLoader extends AsyncTaskLoader<List<Video>> {

    private String mUrl;

    public VideoLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


    @Override
    public List<Video> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        return QueryUtils.fetchNewsDataVideo(mUrl);

    }

}
