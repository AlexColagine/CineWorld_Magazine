package com.alex.android.cineworld.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alex.android.cineworld.pojo.Video;
import com.alex.android.cineworld.utils.Utils;
import com.example.android.cineworld.R;
import com.squareup.picasso.Picasso;

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
        final Video currentVideo = videoList.get(position);
        Picasso.with(mContext)
                .load(Utils.URL_IMAGE_YOUTUBE + currentVideo.getSource() + "/" + Utils.SIZE_IMAGE_YOUTUBE)
                .into(holder.imageVideo);

        holder.imageVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlApp = Utils.INTENT_YOUTUBE_APP + currentVideo.getSource();
                Intent youtube = new Intent(Intent.ACTION_VIEW, Uri.parse(urlApp));

                String urlWeb = Utils.WEB_INTENT_YOUTUBE + currentVideo.getSource();
                Intent webIntent = new Intent(Intent.ACTION_VIEW , Uri.parse(urlWeb));

                if (youtube.resolveActivity(mContext.getPackageManager()) != null) {
                    mContext.startActivity(youtube);
                }
                else {
                    mContext.startActivity(webIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        ImageView imageVideo;

        public VideoViewHolder(View itemView) {
            super(itemView);
            imageVideo = itemView.findViewById(R.id.image_youtube);
        }
    }

}
