package com.example.android.popularmovies.Utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.popularmovies.Object.Movie;
import com.example.android.popularmovies.Object.Review;
import com.example.android.popularmovies.Object.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alessandro on 18/02/2018.
 */

public class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getName();

    public QueryUtils() {
    }

    public static List<Movie> fetchNewsDataMovie(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        return extractMovie(jsonResponse);
    }

    public static List<Review> fetchNewsDataReview(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        return extractReview(jsonResponse);
    }

    public static List<Video> fetchNewsDataVideo(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        return extractVideo(jsonResponse);
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
        }
        return url;
    }

    @SuppressWarnings("ThrowFromFinallyBlock")
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the book JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * JSON for the Movie URL --> Endpoint --> /movie/popular or /movie/top_rated
     *
     * @param response
     * @return a Movie (movieList) with new items.
     */
    private static List<Movie> extractMovie(String response) {
        ArrayList<Movie> movieList = new ArrayList<>();

        if (TextUtils.isEmpty(response)) {
            return null;
        }

        try {

            JSONObject jsonResponse = new JSONObject(response);
            JSONArray resultsArray = jsonResponse.optJSONArray(Utils.RESULTS);
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject currentMovie = resultsArray.optJSONObject(i);
                int id = currentMovie.optInt(Utils.ID_MOVIE);
                String title = currentMovie.optString(Utils.TITLE);
                String image = currentMovie.optString(Utils.POSTER_PATH);
                String plot = currentMovie.optString(Utils.OVERVIEW);
                double rating = currentMovie.optDouble(Utils.VOTE_AVERAGE);
                String releaseDate = currentMovie.optString(Utils.RELEASE_DATE);
                String language = currentMovie.optString(Utils.ORIGINAL_LANGUAGE);
                String backdrop = currentMovie.optString(Utils.BACKDROP_IMAGE);

                movieList.add(new Movie(id, title, image, plot, rating, releaseDate, language, backdrop));
            }

        } catch (JSONException e) {
            Log.e("Queryutils", "Error parsing JSON response [Movie]", e);
        }
        return movieList;
    }

    /**
     * JSON for the Review in a single Movie --> ENDPOINT --> /movie/{id}/reviews
     *
     * @param json
     * @return a Review (reviewList) with new content
     */
    private static List<Review> extractReview(String json) {
        ArrayList<Review> reviewList = new ArrayList<>();

        if (TextUtils.isEmpty(json)) {
            return null;
        }

        try {

            JSONObject jsonResponse = new JSONObject(json);
            JSONArray resultArray = jsonResponse.optJSONArray(Utils.RESULTS);
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject currentReview = resultArray.optJSONObject(i);
                String id = currentReview.optString(Utils.ID_REVIEW);
                String author = currentReview.optString(Utils.AUTHOR_REVIEW);
                String content = currentReview.optString(Utils.CONTENT_REVIEW);
                String url = currentReview.optString(Utils.URL_REVIEW);

                reviewList.add(new Review(id, author, content, url));
            }

        } catch (JSONException e) {
            Log.e("Queryutils", "Error parsing JSON response [Review]", e);
        }

        return reviewList;
    }

    /**
     * JSON for the Video in a single Movie --> ENDPOINT --> /movie/{id}/trailers
     *
     * @param video
     * @return a Video (videoList) with new Trailers for the Movie...
     */
    private static List<Video> extractVideo(String video) {
        ArrayList<Video> videoList = new ArrayList<>();

        if (TextUtils.isEmpty(video)) {
            return null;
        }

        try {

            JSONObject jsonResponse = new JSONObject(video);
            JSONArray resultArray = jsonResponse.optJSONArray(Utils.RESULTS);
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject currentVideo = resultArray.optJSONObject(i);
                String id = currentVideo.optString(Utils.ID_VIDEO);
                String key = currentVideo.optString(Utils.KEY_VIDEO);
                String name = currentVideo.optString(Utils.NAME_VIDEO);
                String site = currentVideo.optString(Utils.SITE_VIDEO);
                int size = currentVideo.optInt(Utils.SIZE_VIDEO);
                String language = currentVideo.optString(Utils.LANGUAGE_VIDEO);
                String country = currentVideo.optString(Utils.COUNTRY_LANGUAGE_VIDEO);
                String type = currentVideo.optString(Utils.TYPE_VIDEO);

                videoList.add(new Video(id, key, name, site, size, language, country, type));
            }

        } catch (JSONException e) {
            Log.e("Queryutils", "Error parsing JSON response [Video]", e);
        }

        return videoList;
    }

}