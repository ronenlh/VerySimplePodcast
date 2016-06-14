package com.ronen.studio08.verysimplepodcast;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.ronen.studio08.verysimplepodcast.itunestop.ItunesTopApi;
import com.ronen.studio08.verysimplepodcast.itunestop.Top;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Ronen on 5/6/16.
 */
public class ItunesNavigationFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private Spinner countrySpinner;
    private String countryCode;
    private ItunesTopApi topService;
    private RecyclerView recyclerView;
    private boolean explicit;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_itunes_navigation, container, false);
        countryCode = "EN";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        topService = retrofit.create(ItunesTopApi.class);
        loadTopFeeds();

        recyclerView = (RecyclerView) view.findViewById(R.id.itunesRecyclerView);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);

        // get explicitness from the SharedPreferences
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        explicit = sharedPref.getBoolean(SettingsFragment.KEY_EXPLICIT, false);
        SharedPreferences.OnSharedPreferenceChangeListener listener =
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                        loadTopFeeds();
                        Log.d("OnSharedPreference", key);
                    }
                };
        //   You must store a strong reference to the listener, or it will be susceptible to garbage collection:
        sharedPref.registerOnSharedPreferenceChangeListener(listener);

        return view;
    }

    private void loadTopFeeds() {

        /* TODO: personalize country code, already begun running into crashes. */
        Call<Top> call = topService.search(countryCode,25,explicit);
        call.enqueue(new Callback<Top>() {
            @Override
            public void onResponse(Call<Top> call, Response<Top> response) {

                ItunesTopRVAdapter rvAdapter = new ItunesTopRVAdapter(getContext(), response.body());
                recyclerView.setAdapter(rvAdapter);
            }

            @Override
            public void onFailure(Call<Top> call, Throwable t) {
                Log.d("RetrofitCaller", t.toString());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        countryCode = getResources().getStringArray(R.array.country_codes)[position];
        loadTopFeeds();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void sharedPrefListener() {

    }
}
