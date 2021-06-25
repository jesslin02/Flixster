package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieDetailsActivity extends AppCompatActivity {

    /* movie whose details the app will display */
    Movie movie;

    /* view objects */
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView ivBackdrop;

    ActivityMovieDetailsBinding activityMDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMDB = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        View view = activityMDB.getRoot();

        setContentView(view);
        // unwrap the movie passed in via intent, using the movie class's simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // assign view objects -- UPDATE: no longer necessary due to view binding class
//        tvTitle = findViewById(R.id.tvTitleDetail);
//        tvOverview = findViewById(R.id.tvOverviewDetail);
//        rbVoteAverage = findViewById(R.id.rbVoteAverage);
//        ivBackdrop = findViewById(R.id.ivBackdrop);

        // set title and overview
        activityMDB.tvTitleDetail.setText(movie.getTitle());
        activityMDB.tvOverviewDetail.setText(movie.getOverview());

        // set star rating bar
        float starRating = movie.getVoteAverage().floatValue() / 2.0f;
        activityMDB.rbVoteAverage.setRating(starRating);

        int radius = 30; // corner radius, higher value = more rounded
        int margin = 10; // crop margin, set to 0 for corners with no crop

        if (this.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            Glide.with(this)
                    .load(movie.getPosterPath())
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .placeholder(R.drawable.flicks_movie_placeholder)
                    .override(600, 900)
                    .into(activityMDB.ivBackdrop);
        } else {
            Glide.with(this)
                    .load(movie.getBackdropPath())
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .placeholder(R.drawable.flicks_backdrop_placeholder)
                    .override(900, 600)
                    .into(activityMDB.ivBackdrop);
        }


    }


}