package com.ronen.studio08.verysimplepodcast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
public class ItunesNavigationFragment extends Fragment implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {
    Spinner countrySpinner;
    String countryCode;
    ItunesTopApi topService;
    RecyclerView recyclerView;
    SwitchCompat explicitSwitch;
    boolean explicit;

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_itunes_navigation, container, false);
        initSpinner();
        countryCode = "EN";

        explicitSwitch = (SwitchCompat) view.findViewById(R.id.explicitSwitch);
        explicitSwitch.setOnCheckedChangeListener(this);


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

        return view;
    }

    private void initSpinner() {
        // populate Spinner
        countrySpinner = (Spinner) view.findViewById(R.id.countrySpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.country_codes, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        countrySpinner.setAdapter(adapter);
        countrySpinner.setOnItemSelectedListener(this);
    }

    private void loadTopFeeds() {

        /* TODO: personalize country code, already begun running into crashes. */
        Call<Top> call = topService.search("EN",25,explicit);
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        explicit = isChecked;
        loadTopFeeds();
    }
}
