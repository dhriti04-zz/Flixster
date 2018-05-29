package com.codepath.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Config {

    //base url
    String imageBaseURL;

    //poster size to use
    String posterSize;

    public Config(JSONObject object ) throws JSONException {
        JSONObject images = object.getJSONObject("images");
        imageBaseURL = images.getString("secure_base_url");
        JSONArray posterSizeOpetions = images.getJSONArray("poster_sizes");
        posterSize = posterSizeOpetions.optString(3, "w342");

    }

    //helper method
    public String getImageUrl (String size, String path){
        return String.format("%s%s%s", imageBaseURL,size,path);
    }

    public String getImageBaseURL() {
        return imageBaseURL;
    }

    public String getPosterSize() {
        return posterSize;
    }
}
