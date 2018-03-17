package com.example.android.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popularmovies.Object.Video;
import com.example.android.popularmovies.R;

import java.util.List;

/**
 * Created by Alessandro on 17/03/2018.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context mContext;
    public List<Video> videoList;

    public VideoAdapter(Context context, List<Video> videoList) {
        this.mContext = context;
        this.videoList = videoList;
    }

    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.video_layout, parent, false);
        return new VideoAdapter.VideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VideoAdapter.VideoViewHolder holder, int position) {
        // picasso per prendere la miniature da youtube con rispettivo URL da richiamare.
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        // ImageView miniatura
        public VideoViewHolder(View itemView) {
            super(itemView);
        }
    }

}
