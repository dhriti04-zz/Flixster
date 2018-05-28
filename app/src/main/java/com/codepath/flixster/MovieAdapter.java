package com.codepath.flixster;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flixster.models.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    //list of movies
    ArrayList<Movie> movies;

    //initialised with array list of movies
    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @NonNull

    //creates and inflates a new view
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater infalter = LayoutInflater.from(context);

        View moviewView = infalter.inflate(R.layout.item_movie, parent, false);

        return new ViewHolder(moviewView);
    }

    //binds an inflated view to a new item
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Movie movie = movies.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());

        System.out.println("working?????");
        //TODO set image using Glide

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    //create viewholder class as a static inner class
    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivPosterImage;
        TextView tvTitle;
        TextView tvOverview;

        public ViewHolder(View itemView) {
            super(itemView);

            //lokup view objects by id
            ivPosterImage = (ImageView) itemView.findViewById(R.id.ivPosterImage);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);

        }
    }
}
