package com.codepath.flixster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.codepath.flixster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity {

    //base url for api
    public final static String API_BASE_URL = "https://api.themoviedb.org/3";

    //parameter name for API
    public final static String API_KEY_PARAM = "api_key";

//    //api key -- TODO move to a secure loc
//    public final static String API_KEY_= "a07e22bc18f5cb106bfe4cc1f83ad8ed";

    //tag
    public final static String TAG = "MovieListActivity";

    //assistant fields
    AsyncHttpClient client;

    //base url
    String imageBaseURL;

    //poster size to use
    String posterSize;

    //list of currently playing movies
    ArrayList<Movie> movies;
    RecyclerView rvMovies;
    MovieAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        //initialise the client
        client = new AsyncHttpClient();

        movies = new ArrayList<>();

        //initialise the adapter (cannot be reinitialised)

        adapter = new MovieAdapter(movies);
        //resolve the recycler view
        rvMovies = (RecyclerView) findViewById(R.id.rvMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);

        getConfiguration();

    }

    //get the list of movies from API
    private void getNowPlaying(){
        String url = API_BASE_URL + "/movie/now_playing";

        //req parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key));

        //execute GET request
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // load the results into movies list
                try {
                    JSONArray results = response.getJSONArray("results");
                    //iterate through results

                    for (int i =0; i< results.length();i++){
                        Movie movie = new Movie(results.getJSONObject(i));
                        movies.add(movie);

                        adapter.notifyItemInserted(movies.size()-1);

                    }
                    Log.i(TAG, String.format("Loaded %s movies",results.length()));
                } catch (JSONException e) {
//                    e.printStackTrace();
                    logError("Failed to parse now playing movies", e, true);
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed to get data from now playing", throwable, true);
            }
        });


    }

    //get config from the API
    private void getConfiguration() {
        String url = API_BASE_URL + "/configuration";

        //req parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key));

        client.get(url,params,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //get image base url
                try {
                    JSONObject images = response.getJSONObject("images");
                    imageBaseURL = images.getString("secure_base_url");
                    JSONArray posterSizeOpetions = images.getJSONArray("poster_sizes");
                    posterSize = posterSizeOpetions.optString(3, "w342");

                    Log.i(TAG, String.format("Loaded congif with imageBaseUrl %s and posterSize %s", imageBaseURL,posterSize));


                    //get the movies list
                    getNowPlaying();

                } catch (JSONException e) {
//                    e.printStackTrace();
                    logError("Failed while parsing config",e, true);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                super.onFailure(statusCode, headers, responseString, throwable);
                logError("Failed getting config", throwable, true);
            }
        });

    }

    //handle errors, log and alert user
    private void logError(String message, Throwable error, boolean alertUSer){

        Log.e(TAG, message, error);
        if (alertUSer){
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
