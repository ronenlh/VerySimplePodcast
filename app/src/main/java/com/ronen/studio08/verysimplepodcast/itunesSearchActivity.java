package com.ronen.studio08.verysimplepodcast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.ronen.studio08.verysimplepodcast.itunes.ItunesSearchService;
import com.ronen.studio08.verysimplepodcast.itunes.Search;
import com.ronen.studio08.verysimplepodcast.itunes.SearchAPI;
import com.ronen.studio08.verysimplepodcast.retrofitCloud.CloudService;
import com.ronen.studio08.verysimplepodcast.retrofitCloud.SampleFeed;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItunesSearchActivity extends AppCompatActivity {

    EditText searchBox;
    Retrofit retrofit;
    SearchAPI service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itunes_search);
        searchBox = (EditText) findViewById(R.id.searchBox);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(SearchAPI.class);
    }

    public void search(View view) {

        Call<Search> call = service.search(searchBox.getText().toString());
        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {

                Log.d("SearchAPI", response.body().toString());

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.itunesRecyclerView);
                recyclerView.setHasFixedSize(true);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(ItunesSearchActivity.this,3);
                recyclerView.setLayoutManager(gridLayoutManager);

                ItunesSeachRVAdapter rvAdapter = new ItunesSeachRVAdapter(ItunesSearchActivity.this, response.body());
                recyclerView.setAdapter(rvAdapter);
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                Log.d("RetrofitCaller", t.toString());
            }
        });
    }
}
