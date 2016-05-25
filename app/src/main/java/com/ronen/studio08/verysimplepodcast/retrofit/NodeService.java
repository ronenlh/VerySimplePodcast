package com.ronen.studio08.verysimplepodcast.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by studio08 on 5/25/2016.
 */
public interface NodeService {
    @GET
    Call<RSS> feed(@Url String feedUrl);
}
