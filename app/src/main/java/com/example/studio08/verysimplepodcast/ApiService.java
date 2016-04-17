package com.example.studio08.verysimplepodcast;

import com.example.studio08.verysimplepodcast.model.Feed;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Created by studio08 on 4/12/2016.
 */
public interface ApiService
{
    @GET("format=xml")
    Call<List<Feed>> createUser();
}