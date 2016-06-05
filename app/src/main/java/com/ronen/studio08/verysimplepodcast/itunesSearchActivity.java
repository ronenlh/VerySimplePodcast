package com.ronen.studio08.verysimplepodcast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.ronen.studio08.verysimplepodcast.itunes.Search;
import com.ronen.studio08.verysimplepodcast.itunes.SearchAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItunesSearchActivity extends AppCompatActivity  {

    EditText searchBox;
    Retrofit retrofit;
    SearchAPI searchService;
    RecyclerView recyclerView;

    Spinner countrySpinner;
    boolean explicit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itunes_search);
        explicit = false; /** TODO: need to add this to savedInstanceState **/
        searchBox = (EditText) findViewById(R.id.searchBox);


        if(findViewById(R.id.itunes_selector_container) != null && savedInstanceState == null) {
            ItunesNavigationFragment itunesNavigationFragment = new ItunesNavigationFragment();
//            itunesNavigationFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().
                    beginTransaction().
                    add(R.id.itunes_selector_container, itunesNavigationFragment).
                    commit();
        }



        retrofit = new Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        searchService = retrofit.create(SearchAPI.class);


    }




    public void search(View view) {
        findViewById(R.id.itunesNavigation).setVisibility(View.GONE);
        Call<Search> call = searchService.search(searchBox.getText().toString());
        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {

                Log.d("SearchAPI", response.body().toString());

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
