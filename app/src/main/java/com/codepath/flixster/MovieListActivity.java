package com.codepath.flixster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity {

    //base url for api
    public final static String API_BASE_URL = "https://api.themoviedb.org/3";

    //parameter name for API
    public final static String API_KEY_PARAM = "api_key";

    //api key
    public final static String API_KEY_= "a07e22bc18f5cb106bfe4cc1f83ad8ed";

    //tag
    public final static String TAG = "MovieListActivity";

    //assistant fields
    AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        //initialise the client
        client = new AsyncHttpClient();
    }

    //get config from the API
    private void getConfiguration() {
        String url = API_BASE_URL + "/configuration";

        //req parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, API_KEY_);

        client.get(url,params,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
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
