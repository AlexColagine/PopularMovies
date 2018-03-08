package com.example.android.popularmovies.Utils;

import android.util.Log;

import com.example.android.popularmovies.Object.Movie;

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

    public static List<Movie> fetchNewsData(String requestUrl) {

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

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
        }
        return url;
    }

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

    /* private static String formatDate(String rawDate) {
        String jsonDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat jsonFormatter = new SimpleDateFormat(jsonDatePattern, Locale.US);
        try {
            Date parsedJsonDate = jsonFormatter.parse(rawDate);
            String finalDatePattern = "MMM d, yyy";
            SimpleDateFormat finalDateFormatter = new SimpleDateFormat(finalDatePattern, Locale.US);
            return finalDateFormatter.format(parsedJsonDate);
        } catch (ParseException e) {
            Log.e("QueryUtils", "Error parsing JSON date: ", e);
            return "";
        }
    } */

    private static List<Movie> extractMovie(String response) {
        ArrayList<Movie> movieList = new ArrayList<>();
        try {

            JSONObject jsonResponse = new JSONObject(response);
            JSONArray resultsArray = jsonResponse.optJSONArray(Utils.RESULTS);
            for (int i = 0 ; i < resultsArray.length() ; i++){
                JSONObject currentMovie = resultsArray.optJSONObject(i);
                String title = currentMovie.optString(Utils.TITLE);
                String image = currentMovie.optString(Utils.POSTER_PATH);
                String plot = currentMovie.optString(Utils.OVERVIEW);
                double rating = currentMovie.optDouble(Utils.VOTE_AVERAGE);
                String releaseDate = currentMovie.optString(Utils.RELEASE_DATE);
                //releaseDate = formatDate(releaseDate);
                String language = currentMovie.optString(Utils.ORIGINAL_LANGUAGE);
                String backdrop = currentMovie.optString(Utils.BACKDROP_IMAGE);

                movieList.add(new Movie(title, image , plot , rating , releaseDate , language , backdrop));
            }

        } catch (JSONException e) {
            Log.e("Queryutils", "Error parsing JSON response", e);
        }
        return movieList;
    }

}