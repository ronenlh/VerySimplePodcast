package com.ronen.studio08.verysimplepodcast.itunes;

import com.ronen.studio08.verysimplepodcast.retrofitCloud.SampleFeed;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by studio08 on 5/25/2016.
 */
public interface SearchAPI {
    // all params listed at https://affiliate.itunes.apple.com/resources/documentation/itunes-store-web-service-search-api/#searching
    @GET("search?media=podcast&country=US&limit=25")
    Call<Search> search(@Query("term") String term);
}
