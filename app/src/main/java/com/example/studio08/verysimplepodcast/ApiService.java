package com.example.studio08.verysimplepodcast;

import com.example.studio08.verysimplepodcast.model.Feed;

import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Created by studio08 on 4/12/2016.
 */
public interface ApiService
{
    @GET("format=xml")
    Call<Feed> createUser(@Body Feed feed);
}