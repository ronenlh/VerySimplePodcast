package com.ronen.studio08.verysimplepodcast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ronen.studio08.verysimplepodcast.itunes.Search;
import com.ronen.studio08.verysimplepodcast.itunes.SearchAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ronen on 5/6/16.
 */
public class ItunesSearchFragment extends Fragment {

    RecyclerView recyclerView;
    SearchAPI searchService;
    View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_itunes_search, container, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        searchService = retrofit.create(SearchAPI.class);


        recyclerView = (RecyclerView) view.findViewById(R.id.itunesSearchRecyclerView);
//        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);

        retrofitCaller();
        return view;
    }

    private void retrofitCaller() {
        Call<Search> call = searchService.search(getArguments().getString("search"));
        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {

                Log.d("SearchAPI", response.body().toString());

                ItunesSeachRVAdapter rvAdapter = new ItunesSeachRVAdapter(getContext(), response.body());
                recyclerView.setAdapter(rvAdapter);
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                Log.d("RetrofitCaller", t.toString());
            }
        });
    }
}
