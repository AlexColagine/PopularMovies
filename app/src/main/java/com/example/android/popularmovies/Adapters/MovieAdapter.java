package com.example.android.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.Object.Movie;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.android.popularmovies.Utils.Utils.IMAGE_SIZE;
import static com.example.android.popularmovies.Utils.Utils.IMAGE_URL;

/**
 * Created by Alessandro on 18/02/2018.
 */


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    final private ListItemClickListener mOnClickListener;
    private Context mContext;
    public List<Movie> movieList;

    public MovieAdapter (Context context, List<Movie> movieList , ListItemClickListener listener ){
        this.mContext = context;
        this.mOnClickListener = listener;
        this.movieList = movieList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.gridlayout_item , parent , false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie currentMovie = movieList.get(position);
        Picasso.with(mContext)
                .load(IMAGE_URL
                        .concat(IMAGE_SIZE[2])
                        .concat(currentMovie.getImage()))
                .placeholder(R.drawable.progress_animation)
                .into(holder.poster);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public Movie getItem(int position){
        return movieList.get(position);
    }



    /**
     * The interface that receives onClick messages.
     */
    public interface ListItemClickListener {
        void onListItemClick(int position);
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder implements OnClickListener{

        ImageView poster;

        MovieViewHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.poster_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = getAdapterPosition();
            mOnClickListener.onListItemClick(id);
        }
    }
}
