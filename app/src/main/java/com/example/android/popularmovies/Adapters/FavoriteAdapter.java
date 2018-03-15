package com.example.android.popularmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.Database.MovieContract;
import com.example.android.popularmovies.Object.Movie;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.UI.DetailActivity;
import com.squareup.picasso.Picasso;

import static com.example.android.popularmovies.Utils.Utils.IMAGE_SIZE;
import static com.example.android.popularmovies.Utils.Utils.IMAGE_URL;
import static com.example.android.popularmovies.Utils.Utils.MOVIE;

/**
 * Created by Alessandro on 10/03/2018.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    // Holds on to the cursor to display the waitlist
    private Cursor mCursor;
    private Context mContext;

    /**
     * Constructor using the context and the db cursor
     *
     * @param context the calling context/activity
     * @param cursor  the db cursor with password to display
     */
    public FavoriteAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.favorite_item, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteAdapter.FavoriteViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        // Update the view holder with the information needed to display
        String title_db = mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE));
        double rating_db = mCursor.getDouble(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATING));
        String ratingString = String.valueOf(rating_db);
        String date_db = mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_DATE));
        String poster_db = mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_IMAGE_POSTER));
        String backdrop_db = mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_IMAGE_BACKDROP));

        String plot_db = mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_PLOT));
        String language_db = mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_LANGUAGE));
        int movie__id_db = mCursor.getInt(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID));
        // Retrieve the id from the cursor and
        long id = mCursor.getLong(mCursor.getColumnIndex(MovieContract.MovieEntry._ID));

        holder.titleTextView.setText(title_db);
        holder.ratingTextView.setText(ratingString);
        holder.dateTextView.setText(date_db);


        Picasso.with(mContext)
                .load(IMAGE_URL
                        .concat(IMAGE_SIZE[5])
                        .concat(poster_db))
                .into(holder.posterImageView);

        Picasso.with(mContext)
                .load(IMAGE_URL
                        .concat(IMAGE_SIZE[5])
                        .concat(backdrop_db))
                .into(holder.backdropImageView);

        holder.itemView.setTag(id);

        final Movie movieFavorite = new Movie(movie__id_db, title_db, poster_db, plot_db, rating_db, date_db, language_db, backdrop_db);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendFavorite = new Intent(mContext, DetailActivity.class);
                sendFavorite.putExtra(MOVIE, movieFavorite);
                mContext.startActivity(sendFavorite);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        }
        return 0;
    }

    /**
     * Swaps the Cursor currently held in the adapter with a new one
     * and triggers a UI refresh
     *
     * @param newCursor the new cursor that will replace the existing one
     */
    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView ratingTextView;
        TextView dateTextView;
        ImageView posterImageView;
        ImageView backdropImageView;
        ConstraintLayout layout;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews
         *
         * @param itemView The View that you inflated in
         *                 {@link FavoriteAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public FavoriteViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title_favorite);
            ratingTextView = (TextView) itemView.findViewById(R.id.rating_favorite);
            dateTextView = (TextView) itemView.findViewById(R.id.date_favorite);
            posterImageView = (ImageView) itemView.findViewById(R.id.poster_favorite);
            backdropImageView = (ImageView) itemView.findViewById(R.id.backdrop_favorite);
            layout = (ConstraintLayout) itemView.findViewById(R.id.constraint);
        }

    }
}
