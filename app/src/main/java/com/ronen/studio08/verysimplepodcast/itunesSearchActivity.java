package com.ronen.studio08.verysimplepodcast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.ronen.studio08.verysimplepodcast.itunes.Search;
import com.ronen.studio08.verysimplepodcast.itunes.SearchAPI;
import com.ronen.studio08.verysimplepodcast.itunestop.ItunesTopApi;
import com.ronen.studio08.verysimplepodcast.itunestop.Top;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItunesSearchActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    EditText searchBox;
    Retrofit retrofit;
    SearchAPI searchService;
    ItunesTopApi topService;
    RecyclerView recyclerView;
    SwitchCompat explicitSwitch;
    boolean explicit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itunes_search);
        explicit = false; /** TODO: need to add this to savedInstanceState **/
        searchBox = (EditText) findViewById(R.id.searchBox);
        explicitSwitch = (SwitchCompat) findViewById(R.id.explicitSwitch);
        explicitSwitch.setOnCheckedChangeListener(this);

        // populate Spinner
        Spinner spinner = (Spinner) findViewById(R.id.countrySpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country_codes, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        topService = retrofit.create(ItunesTopApi.class);
        searchService = retrofit.create(SearchAPI.class);

        recyclerView = (RecyclerView) findViewById(R.id.itunesRecyclerView);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(ItunesSearchActivity.this,3);
        recyclerView.setLayoutManager(gridLayoutManager);

        loadTopFeeds();
    }

    private void loadTopFeeds() {
        Call<Top> call = topService.search("EN",25,explicit);
        call.enqueue(new Callback<Top>() {
            @Override
            public void onResponse(Call<Top> call, Response<Top> response) {

                Log.d("SearchAPI", response.body().toString());

                ItunesTopRVAdapter rvAdapter = new ItunesTopRVAdapter(ItunesSearchActivity.this, response.body());
                recyclerView.setAdapter(rvAdapter);
            }

            @Override
            public void onFailure(Call<Top> call, Throwable t) {
                Log.d("RetrofitCaller", t.toString());
            }
        });
    }

    public void search(View view) {

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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            explicit = isChecked;
        loadTopFeeds();
    }
}
