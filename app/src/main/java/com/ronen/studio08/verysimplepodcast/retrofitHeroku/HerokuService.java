package com.ronen.studio08.verysimplepodcast.retrofitHeroku;

import com.ronen.studio08.verysimplepodcast.retrofit.RSS;

import retrofit2.*;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by studio08 on 5/25/2016.
 */
public interface HerokuService {
    // Every method must have an HTTP annotation that provides the request method and relative URL.
    // There are five built-in annotations: GET, POST, PUT, DELETE, and HEAD.
    // The relative URL of the resource is specified in the annotation.
    @GET("podcasts/")
    Call<ResponseFeeds> feed();
}
