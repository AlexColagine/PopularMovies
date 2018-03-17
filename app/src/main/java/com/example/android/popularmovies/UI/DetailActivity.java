package com.example.android.popularmovies.UI;

import android.content.ContentValues;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.Adapters.ReviewAdapter;
import com.example.android.popularmovies.Adapters.VideoAdapter;
import com.example.android.popularmovies.Database.MovieContract;
import com.example.android.popularmovies.Object.Movie;
import com.example.android.popularmovies.Object.Review;
import com.example.android.popularmovies.Object.Video;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.popularmovies.Utils.Utils.IMAGE_SIZE;
import static com.example.android.popularmovies.Utils.Utils.IMAGE_URL;
import static com.example.android.popularmovies.Utils.Utils.MOVIE;

/**
 * Created by Alessandro on 19/02/2018.
 */

public class DetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public Movie movieList;
    Context mContext;
    private ReviewAdapter reviewAdapter;
    private VideoAdapter videoAdapter;
    SwipeRefreshLayout swipeReview;
    SwipeRefreshLayout swipeVideo;

    private static final int LOADER_REVIEW_ID = 1;
    private LoaderManager.LoaderCallbacks<List<Review>> reviewLoader = new LoaderManager.LoaderCallbacks<List<Review>>() {
        @Override
        public Loader<List<Review>> onCreateLoader(int id, Bundle args) {
            return /*ReviewLoader(mContext , buildUrlReview_Video()) */ null;
        }

        @Override
        public void onLoadFinished(Loader<List<Review>> loader, List<Review> data) {
            reviewAdapter.reviewList.clear();
        }

        @Override
        public void onLoaderReset(Loader<List<Review>> loader) {
            getSupportLoaderManager().restartLoader(LOADER_REVIEW_ID, null, this);
        }
    };
    private static final int LOADER_VIDEO_ID = 2;
    private LoaderManager.LoaderCallbacks<List<Video>> videoLoader = new LoaderManager.LoaderCallbacks<List<Video>>() {
        @Override
        public Loader<List<Video>> onCreateLoader(int id, Bundle args) {
            return /*VideoLoader(mContext , buildUrlReview_Video()) */ null;
        }

        @Override
        public void onLoadFinished(Loader<List<Video>> loader, List<Video> data) {
            videoAdapter.videoList.clear();
        }

        @Override
        public void onLoaderReset(Loader<List<Video>> loader) {
            getSupportLoaderManager().restartLoader(LOADER_VIDEO_ID, null, this);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /**
         *  Get object Movie position
         */
        Bundle data = getIntent().getExtras();
        movieList = data.getParcelable(MOVIE);

        updateDetailUi();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_favorite);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertFavorite();
            }
        });

        getSupportLoaderManager().initLoader(LOADER_REVIEW_ID, null, reviewLoader);
        getSupportLoaderManager().initLoader(LOADER_VIDEO_ID, null, videoLoader);

    }

    /**
     * It used for update the UI of the Detail Movie
     */
    private void updateDetailUi() {
        ImageView imageBackdrop = (ImageView) findViewById(R.id.image_backdrop);
        Picasso.with(this)
                .load(IMAGE_URL
                        .concat(IMAGE_SIZE[5])
                        .concat(movieList.getBackdrop()))
                .into(imageBackdrop);

        TextView title = (TextView) findViewById(R.id.title_tv);
        title.setText(movieList.getTitle());

        TextView date = (TextView) findViewById(R.id.date_tv);
        date.setText(movieList.getDate());

        TextView rating = (TextView) findViewById(R.id.rating_tv);
        rating.setText(String.valueOf(movieList.getRating()) + "\n" + "/10");

        TextView language = (TextView) findViewById(R.id.language_tv);
        language.setText(movieList.getLanguage());

        TextView plot = (TextView) findViewById(R.id.plot_tv);
        plot.setText(movieList.getPlot());
    }

    /**
     * It used for update the UI of the Review Movie
     */
    private void updateReviewUi() {
        swipeReview = (SwipeRefreshLayout) findViewById(R.id.swiperefresh_review);
        swipeReview.setOnRefreshListener(this);

        RecyclerView reviewRecycler = (RecyclerView) findViewById(R.id.recyclerReview);
        reviewRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        reviewRecycler.setHasFixedSize(true);
        reviewAdapter = new ReviewAdapter(mContext, new ArrayList<Review>());
        reviewRecycler.setAdapter(reviewAdapter);
    }

    /**
     * It used for update the UI of the Video Movie
     */
    private void updateVideoUI() {
        swipeVideo = (SwipeRefreshLayout) findViewById(R.id.swiperefresh_video);
        swipeVideo.setOnRefreshListener(this);

        RecyclerView videoRecycler = (RecyclerView) findViewById(R.id.recyclerVideo);
        videoRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        videoRecycler.setHasFixedSize(true);
        videoAdapter = new VideoAdapter(mContext, new ArrayList<Video>());
        videoRecycler.setAdapter(videoAdapter);
    }

    /**
     * It used to verify if there is a connection (WiFi or data mobile)
     */
    private void Connectivity() {
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            getSupportLoaderManager().initLoader(LOADER_REVIEW_ID, null, reviewLoader);
            getSupportLoaderManager().initLoader(LOADER_VIDEO_ID, null, videoLoader);
        } else {
            // loadingIndicator.setVisibility(View.GONE);
            // mEmptyTextView.setText(R.string.no_internet_connection);
        }
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
        values.put(MovieContract.MovieEntry.COLUMN_LANGUAGE, movieList.getLanguage());
        getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);
        Toast.makeText(this, "Movie saved in database", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {

    }
}
