package com.example.studio08.verysimplepodcast.retrofit;




import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by studio08 on 4/12/2016.
 */
public interface ApiService
{
    @GET("{feed}?format=xml")
    Call<RSS> feed(@Path("feed") String feedName);
}