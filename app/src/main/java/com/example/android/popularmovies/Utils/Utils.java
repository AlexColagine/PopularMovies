package com.example.android.popularmovies.Utils;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import com.example.android.popularmovies.Object.Movie;
/**
 * Created by Alessandro on 02/03/2018.
 */

public class Utils extends AppCompatActivity {

    public static Movie currentMovie;
    /**
     * Strings to create URL in buildUrlMovie method.
     * * @API_KEY
     * ** @TMDB_MOVIE_URL
     * *** @QUERY_PARAM ---> are unique for all url method (Movie , Review , Video)
     */
    public static final String TMDB_MOVIE_URL = "https://api.themoviedb.org/3/movie/";
    public static final String QUERY_PARAM = "api_key";
    public static final String API_KEY = "";
    public static final String IMAGE_URL = "http://image.tmdb.org/t/p/";
    public static String QUERY_POPULAR_PATH = "popular";
    public static String QUERY_TOP_RATED_PATH = "top_rated";
    public static String QUERY_MOVIE = "";

    /**
     * Vector with different size of ImageView
     */
    public static final String[] IMAGE_SIZE = {"w92", "w154", "w185", "w342", "w500", "w780", "original"};

    /**
     * Strings to create URL in buildUrlReviewVideo method.
     */
    //public static String QUERY_ID_PATH = String.valueOf(currentMovie.getId());
    public static String QUERY_REVIEW_PATH = "reviews";
    public static String QUERY_VIDEO_PATH = "trailers";

    /**
     * Strings for the JSON Movie results
     */
    public static final String RESULTS = "results";
    public static final String ID_MOVIE = "id";
    public static final String TITLE = "title";
    public static final String POSTER_PATH = "poster_path";
    public static final String OVERVIEW = "overview";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String RELEASE_DATE = "release_date";
    public static final String ORIGINAL_LANGUAGE = "original_language";
    public static final String BACKDROP_IMAGE = "backdrop_path";

    /**
     * String for the JSON Review results
     */
    public static final String ID_REVIEW = "id";
    public static final String AUTHOR_REVIEW = "author";
    public static final String CONTENT_REVIEW = "content";
    public static final String URL_REVIEW = "url";

    /**
     * String for the JSON Video results
     */
    public static final String ID_VIDEO = "id";
    public static final String LANGUAGE_VIDEO = "iso_639_1";
    public static final String COUNTRY_LANGUAGE_VIDEO = "iso_3166_1";
    public static final String KEY_VIDEO = "key";
    public static final String NAME_VIDEO = "name";
    public static final String SITE_VIDEO = "site";
    public static final String SIZE_VIDEO = "size";
    public static final String TYPE_VIDEO = "type";

    /**
     * String/key for the parcelable object used in ListMovie(PopularRated) , DetailActivity and Favorite.
     */
    public static final String MOVIE = "Movie";

    /**
     * @param context
     * @return an integer value for the show a columns of the GridLayout in RecyclerView
     */
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }

    /**
     * @param queryMovie = The keyword that will be queried for.
     * @return The String to use to query the TMDB server.
     */
    public static String buildUrlMovie(String queryMovie) {
        Uri baseUri = Uri.parse(TMDB_MOVIE_URL)
                .buildUpon()
                .appendPath(queryMovie)
                .appendQueryParameter(QUERY_PARAM, API_KEY)
                .build();
        return baseUri.toString();
    }

    public static String buildUrlReview_Video(String query) {
        Uri baseUri = Uri.parse(TMDB_MOVIE_URL)
                .buildUpon()
                // .appendPath(QUERY_ID_PATH)
                .appendPath(query)
                .appendQueryParameter(QUERY_PARAM, API_KEY)
                .build();
        return baseUri.toString();
    }

}
