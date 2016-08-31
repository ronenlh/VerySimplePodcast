package com.ronen.studio08.verysimplepodcast.model.retrofit;

import com.ronen.studio08.verysimplepodcast.model.itunesSearchModelClass.Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LookupAPI {
    @GET("lookup?entity=podcast")
    Call<Search> search(@Query("id") String id);
}
