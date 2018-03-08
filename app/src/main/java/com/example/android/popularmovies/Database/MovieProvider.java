package com.example.android.popularmovies.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Alessandro on 28/02/2018.
 */

public class MovieProvider  extends ContentProvider {

    public static final String LOG_TAG = MovieProvider.class.getSimpleName();

    private static final int MOVIE = 100;
    private static final int MOVIE_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // The content URI of the form "content://com.example.android.myinventory/stock"
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_INVENTORY, MOVIE );

        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        // For example, "content://com.example.android.myinventory/stock/3"
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_INVENTORY + "/#", MOVIE_ID);
    }

    MovieDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        // This cursor will hold the result of the query
        Cursor cursor;
        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIE:
                cursor = database.query(MovieContract.MovieEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case MOVIE_ID:
                selection = MovieContract.MovieEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(MovieContract.MovieEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIE:
                return MovieContract.MovieEntry.CONTENT_LIST_TYPE;
            case MOVIE_ID:
                return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIE:
                return insertMovie(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertMovie(Uri uri, ContentValues values) {

        String title = values.getAsString(MovieContract.MovieEntry.COLUMN_TITLE);
        if (title == null) {
            throw new IllegalArgumentException("Movie requires a title");
        }

        String rating = values.getAsString(MovieContract.MovieEntry.COLUMN_RATING);
        if (rating == null) {
            throw new IllegalArgumentException("Movie requires a rating");
        }

        String date = values.getAsString(MovieContract.MovieEntry.COLUMN_DATE);
        if (date == null) {
            throw new IllegalArgumentException("Movie requires a date");
        }

        String image = values.getAsString(MovieContract.MovieEntry.COLUMN_IMAGE);
        if (image == null) {
            throw new IllegalArgumentException("Movie requires a image");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(MovieContract.MovieEntry.TABLE_NAME, null , values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }


        getContext().getContentResolver().notifyChange(uri, null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIE:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE_ID:
                // Delete a single row given by the ID in the URI
                selection = MovieContract.MovieEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Return the number of rows deleted
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    /*   @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIE:
                return updatePsw(uri, contentValues, selection, selectionArgs);
            case MOVIE_ID:
                selection = MovieContract.MovieEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updatePsw(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updatePsw(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(MovieContract.MovieEntry.COLUMN_NAME)) {
            String name = values.getAsString(MovieContract.MovieEntry.COLUMN_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Item requires a name");
            }
        }

        if (values.containsKey(MovieContract.MovieEntry.COLUMN_PASSWORD)) {
            String password = values.getAsString(MovieContract.MovieEntry.COLUMN_PASSWORD);
            if (password == null) {
                throw new IllegalArgumentException("Item requires a password");
            }
        }

        if (values.containsKey(PasswordContract.PasswordEntry.COLUMN_EMAIL)) {
            String email = values.getAsString(PasswordContract.PasswordEntry.COLUMN_EMAIL);
            if (email == null) {
                throw new IllegalArgumentException("Item requires a email");
            }
        }

        if (values.containsKey(MovieContract.MovieEntry.COLUMN_WEB)) {
            String web = values.getAsString(MovieContract.MovieEntry.COLUMN_WEB);
            if (web == null) {
                throw new IllegalArgumentException("Item requires a website");
            }
        }
        if (values.containsKey(MovieContract.MovieEntry.COLUMN_ACCOUNT)) {
            String account = values.getAsString(MovieContract.MovieEntry.COLUMN_ACCOUNT);
            if (account == null) {
                throw new IllegalArgumentException("Item requires an account");
            }
        }

        if (values.containsKey(MovieContract.MovieEntry.COLUMN_DATE)) {
            String date = values.getAsString(MovieContract.MovieEntry.COLUMN_DATE);
            if (date == null) {
                throw new IllegalArgumentException("Item requires a date");
            }
        }
        if (values.containsKey(MovieContract.MovieEntry.COLUMN_DESCRIZIONE)) {
            String descrizione = values.getAsString(MovieContract.MovieEntry.COLUMN_DESCRIZIONE);
            if (descrizione == null) {
                throw new IllegalArgumentException("Item requires a description");
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }
        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(MovieContract.MovieEntry.TABLE_NAME, values, selection, selectionArgs);
        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Return the number of rows updated
        return rowsUpdated;
    } */
}
