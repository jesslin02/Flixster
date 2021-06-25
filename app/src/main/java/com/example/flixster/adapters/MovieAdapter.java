package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    /* where the adapter is being constructed from */
    Context context;
    /* list of movies */
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    /**
     * inflate a layout from XML and return the view holder
     */
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    /*
    take data at position and put it into the view inside the view holder
     */
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        // get the movie at the position that was passed in
        Movie movie = movies.get(position);
        // bind the movie data in the view holder
        holder.bind(movie);
    }

    /*
    returns the total number of items in the list
     */
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            // allow us to decide what happens when an item is clicked
            itemView.setOnClickListener(this);
        }

        /**
         *
         * @param movie
         */
        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop
            if (context.getResources().getConfiguration().orientation
                    == Configuration.ORIENTATION_LANDSCAPE) {
                Glide.with(context)
                        .load(movie.getBackdropPath())
                        .transform(new RoundedCornersTransformation(radius, margin))
                        .placeholder(R.drawable.flicks_backdrop_placeholder)
                        .override(750, 500)
                        .into(ivPoster);
            } else {
                Glide.with(context)
                        .load(movie.getPosterPath())
                        .transform(new RoundedCornersTransformation(radius, margin))
                        .placeholder(R.drawable.flicks_movie_placeholder)
                        .override(300, 450)
                        .into(ivPoster);
            }
        }
        /*
        when user clicks on a row, show MovieDetailsActivity for the selected movie
         */
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) { // checks that item exists in the view
                Movie selectedMovie = movies.get(position);
                // create intent for new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                // serialize movie using parceler, use movie class's short name as a key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(selectedMovie));
                // show the activity
                context.startActivity(intent);
            }

        }
    }
}
