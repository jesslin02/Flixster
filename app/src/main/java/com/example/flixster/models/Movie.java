package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
represents a movie object, constructed from the JSON object in the results array from the API
 */
public class Movie {
    /* relative url path (back half of url) to the movie poster image */
    String posterPath;
    /* relative url path (back half of url) to the movie backdrop image for use in landscape mode*/
    String backdropPath;
    /* title of movie */
    String title;
    /* movie description */
    String overview;

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.title = jsonObject.getString("title");
        this.overview = jsonObject.getString("overview");
    }

    public static List<Movie> fromJSONArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++) {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    /* returns full poster path url */
    public String getPosterPath() {
        // hardcoding url here as a shortcut but the better way of doing this is
        // to make an API request to the TMDb configurations API response and look
        // at the available sizes first, appending the size to the base url, then appending
        // the relative path (posterPath) to the end
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath() {
        // same hardcoding as getPosterPath
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
