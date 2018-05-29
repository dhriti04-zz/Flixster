package com.codepath.flixster;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.flixster.models.Config;
import com.codepath.flixster.models.Movie;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    //list of movies
    ArrayList<Movie> movies;
    Config config;
    Context context;

    //initialised with array list of movies
    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
        System.out.println("working????? + movies" + movies);
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    //creates and inflates a new view
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View movieView = inflater.inflate(R.layout.item_movie, parent, false);

        return new ViewHolder(movieView);
    }

    //binds an inflated view to a new item
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Movie movie = movies.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());

        System.out.println("working?????");

        boolean isPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        //TODO set image using Glide
        String imageUrl = null;

        if (isPortrait){
            imageUrl = config.getImageUrl(config.getPosterSize(),movie.getPosterPath());

        } else {
            imageUrl = config.getImageUrl(config.getBackdropSize(), movie.getBackdropPath());

        }

        //get the correct placeholder
        int placeholderId = isPortrait ? R.drawable.flicks_movie_placeholder : R.drawable.flicks_backdrop_placeholder;
        ImageView imageView = isPortrait ? holder.ivPosterImage : holder.ivBackdropImage;



        //load image using glide
        Glide.with(context)
                .load(imageUrl)
                .bitmapTransform(new RoundedCornersTransformation(context,25,0))
                .placeholder(R.drawable.flicks_movie_placeholder)
                .error(placeholderId)
                .into(imageView);



    }

    @Override
    public int getItemCount() {
        System.out.println("working?????" + movies.size());
        return movies.size();
    }

    //create viewholder class as a static inner class
    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivPosterImage;
        ImageView ivBackdropImage;
        TextView tvTitle;
        TextView tvOverview;

        public ViewHolder(View itemView) {
            super(itemView);

            //lookup view objects by id
            ivPosterImage = (ImageView) itemView.findViewById(R.id.ivPosterImage);
            ivBackdropImage = (ImageView) itemView.findViewById(R.id.ivBackdrop);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);

        }
    }
}
