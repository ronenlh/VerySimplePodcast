package com.ronen.studio08.verysimplepodcast;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.ronen.studio08.verysimplepodcast.model.itunesNavModelClass.Entry;
import com.ronen.studio08.verysimplepodcast.model.itunesNavModelClass.ItunesTopApi;
import com.ronen.studio08.verysimplepodcast.model.itunesNavModelClass.Top;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Ronen on 5/6/16.
 */
public class ItunesNavigationFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private String countryCode = "US";
    private ItunesTopApi topService;
    private RecyclerView recyclerView;
    private boolean isExplicit;

    private View view;

    private static final String TAG = "ItunesNavigation";

    interface OnNavigationItemClickedListener {
        void OnNavigationItemClicked(Entry entry);
    }

    private static OnNavigationItemClickedListener mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnNavigationItemClickedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnNavigationItemClickedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_itunes_navigation, container, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        topService = retrofit.create(ItunesTopApi.class);
        loadTopFeeds();

        recyclerView = (RecyclerView) view.findViewById(R.id.itunesRecyclerView);
        recyclerView.setHasFixedSize(true);

        boolean isOrientationPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        int numberOfRows = isOrientationPortrait? 3 : 4;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), numberOfRows);
        recyclerView.setLayoutManager(gridLayoutManager);

        // get explicitness from the SharedPreferences
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        isExplicit = sharedPref.getBoolean(SettingsFragment.KEY_EXPLICIT, false);
        countryCode = sharedPref.getString(SettingsFragment.KEY_COUNTRY, "US");
        SharedPreferences.OnSharedPreferenceChangeListener listener =
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                        loadTopFeeds();
                        Log.d("OnSharedPreference", key);
                    }
                };
        //   You must store a strong reference to the mCallback, or it will be susceptible to garbage collection:
        sharedPref.registerOnSharedPreferenceChangeListener(listener);

        return view;
    }

    private void loadTopFeeds() {

        /* TODO: personalize country code, already begun running into crashes. */
        Call<Top> call = topService.search(countryCode,25, isExplicit);
        call.enqueue(new Callback<Top>() {
            @Override
            public void onResponse(Call<Top> call, Response<Top> response) {

                ItunesNavigationAdapter rvAdapter = new ItunesNavigationAdapter(getContext(), response.body());
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
        countryCode = getResources().getStringArray(R.array.country_names)[position];
        loadTopFeeds();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static class ItunesNavigationHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView thumbnail;
        final TextView title;
        final TextView artist;
        final Top top;

        public ItunesNavigationHolder(View itemView, Top top) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.top = top;
            thumbnail = (ImageView) itemView.findViewById(R.id.add_thumbnail);
            title = (TextView) itemView.findViewById(R.id.podTitle);
            artist = (TextView) itemView.findViewById(R.id.podArtist);
        }


        @Override
        public void onClick(View v) {
            // there is no direct link to the RSS feed in top podcasts API so need to workaround
            Entry entry = top.getFeed().getEntry().get(getAdapterPosition());
            Log.d(TAG, entry.getImName().getLabel().toString());
            mCallback.OnNavigationItemClicked(entry);
        }
    }

    public class ItunesNavigationAdapter extends RecyclerView.Adapter<ItunesNavigationHolder>{
        private final Context context;
        private final Top top;

        public ItunesNavigationAdapter(Context context, Top top) {
            this.context = context;
            this.top = top;
        }


        @Override
        public ItunesNavigationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.itunes_feed_row, parent, false);
            return new ItunesNavigationHolder(itemView, top);
        }

        @Override
        public void onBindViewHolder(ItunesNavigationHolder holder, int position) {
            // onBindViewHolder is called when the SO binds the view with the data -- or, in other words, the data is shown in the UI.
            Entry entry = top.getFeed().getEntry().get(position);
            holder.title.setText(entry.getImName().getLabel());
            holder.artist.setText(entry.getImArtist().getLabel());
            Picasso.with(context)
                    .load(entry.getImImage().get(2).getLabel())
                    // error(R.drawable.ic_broken_image_black_24dp).
                    .into(holder.thumbnail);
        }

        @Override
        public int getItemCount() {
            return top.getFeed().getEntry().size();
        }
    }
}
