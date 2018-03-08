package com.example.android.popularmovies.Utils;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

/**
 * Created by Alessandro on 02/03/2018.
 */

public class Utils extends AppCompatActivity{

    /**
     * Strings to create URL in buildUrl method.
     */
    public static final String TMDB_MOVIE_URL = "https://api.themoviedb.org/3/movie/";
    public static final String QUERY_PARAM = "api_key";
    public static final String API_KEY = "f138fc50065f4f65157a63739530f7c3";
    public static final String IMAGE_URL = "http://image.tmdb.org/t/p/";
    public static String QUERY_POPULAR_PATH = "popular";
    public static String QUERY_TOP_RATED_PATH = "top_rated";
    public static String QUERY_MOVIE = "";

    /**
     * Vector with different size of ImageView
     */
    public static final String[] IMAGE_SIZE = {"w92" , "w154" , "w185" , "w342" , "w500" , "w780"};

    /**
     * Strings for the JSON results
     */
    public static final String RESULTS = "results";
    public static final String TITLE = "title";
    public static final String POSTER_PATH = "poster_path";
    public static final String OVERVIEW = "overview";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String RELEASE_DATE = "release_date";
    public static final String ORIGINAL_LANGUAGE = "original_language";
    public static final String BACKDROP_IMAGE = "backdrop_path";

    /**
     * String/key for the parcelable object used in ListMovie and DetailActivity.
     */
    public static final String MOVIE = "Movie";

    /**
     *
     * @param context
     * @return an integer value for the show a columns of the GridLayout in RecyclerView
     */
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }

    /**
     *
     * @param queryMovie = The keyword that will be queried for.
     * @return The String to use to query the TMDB server.
     */
    public static String buildUrl (String queryMovie){
        Uri baseUri = Uri.parse(TMDB_MOVIE_URL)
                .buildUpon()
                .appendPath(queryMovie)
                .appendQueryParameter(QUERY_PARAM , API_KEY)
                .build();
        return baseUri.toString();
    }

}
