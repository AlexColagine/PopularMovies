package com.example.android.popularmovies.Database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.android.popularmovies.Utils.Utils;

/**
 * Created by Alessandro on 27/02/2018.
 */

public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_INVENTORY = "movie";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        public static final String TABLE_NAME = "Movie";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_MOVIE_ID = Utils.ID_REVIEW;
        public static final String COLUMN_TITLE = Utils.TITLE;
        public static final String COLUMN_RATING = Utils.VOTE_AVERAGE;
        public static final String COLUMN_DATE = Utils.RELEASE_DATE;
        public static final String COLUMN_IMAGE_POSTER = Utils.POSTER_PATH;
        public static final String COLUMN_IMAGE_BACKDROP = Utils.BACKDROP_IMAGE;
        public static final String COLUMN_PLOT = Utils.OVERVIEW;
        public static final String COLUMN_LANGUAGE = Utils.ORIGINAL_LANGUAGE;

    }

}
