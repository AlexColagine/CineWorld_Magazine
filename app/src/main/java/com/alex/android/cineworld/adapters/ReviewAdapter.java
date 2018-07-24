package com.alex.android.cineworld.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alex.android.cineworld.R;
import com.alex.android.cineworld.pojo.Review;

import java.util.List;


/**
 * Created by Alessandro on 17/03/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context mContext;
    public List<Review> reviewList;

    public ReviewAdapter(Context context, List<Review> reviewList) {
        this.mContext = context;
        this.reviewList = reviewList;
    }

    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.review_layout, parent, false);
        return new ReviewAdapter.ReviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewViewHolder holder, int position) {
        final Review currentReview = reviewList.get(position);
        holder.author.setText(currentReview.getAuthor());
        holder.content.setText(currentReview.getContent());

        holder.constraintReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent urlContent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentReview.getUrl()));
                mContext.startActivity(urlContent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout constraintReview;
        TextView author;
        TextView content;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            constraintReview = (ConstraintLayout) itemView.findViewById(R.id.constraint_review);
            author = (TextView) itemView.findViewById(R.id.author_tv);
            content = (TextView) itemView.findViewById(R.id.content_tv);
        }
    }

}
