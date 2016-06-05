package com.ronen.studio08.verysimplepodcast.itunestop;

import com.ronen.studio08.verysimplepodcast.itunes.Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by studio08 on 5/25/2016.
 */
public interface ItunesTopApi {
    // all params listed at https://rss.itunes.apple.com/us/?urlDesc=
    @GET("{country}/rss/toppodcasts/limit={limit}/explicit={explicit}/json")
    Call<Top> search(@Path("country") String country, @Path("limit") int limit, @Path("explicit") boolean explicit);
}
