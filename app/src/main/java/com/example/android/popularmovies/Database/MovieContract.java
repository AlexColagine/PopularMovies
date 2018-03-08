package com.example.android.popularmovies.Database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Alessandro on 27/02/2018.
 */

class MovieContract {

    static final String CONTENT_AUTHORITY = "com.example.android.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    static final String PATH_INVENTORY = "movie";

    static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);
        static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        static final String TABLE_NAME = "Password";

        static final String _ID = BaseColumns._ID;
        static final String COLUMN_TITLE = "title";
        static final String COLUMN_RATING = "rating";
        static final String COLUMN_DATE = "date";
        static final String COLUMN_IMAGE = "poster";

    }

}
