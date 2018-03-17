package com.example.android.popularmovies.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alessandro on 28/02/2018.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_MOVIE = "CREATE TABLE " +
                MovieContract.MovieEntry.TABLE_NAME + "(" +
                // MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY," +
                MovieContract.MovieEntry.COLUMN_TITLE + " TEXT," +
                MovieContract.MovieEntry.COLUMN_RATING + " REAL," +
                MovieContract.MovieEntry.COLUMN_DATE + " TEXT," +
                MovieContract.MovieEntry.COLUMN_PLOT + " TEXT," +
                MovieContract.MovieEntry.COLUMN_LANGUAGE + " TEXT," +
                MovieContract.MovieEntry.COLUMN_IMAGE_BACKDROP + " TEXT," +
                MovieContract.MovieEntry.COLUMN_IMAGE_POSTER + " TEXT )";
        db.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME;
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }
}
