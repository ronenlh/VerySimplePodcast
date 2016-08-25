package com.ronen.studio08.verysimplepodcast.controllers;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ronen.studio08.verysimplepodcast.R;
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
 * Created by Ronen on 25/8/16.
 */
public class ItunesGalleryFragment  extends Fragment {

    private static final String TAG = "ItunesGalleryFragment";
    RecyclerView mRecyclerView;
    private ItunesTopApi topService;
    private boolean isExplicit;
    private String countryCode = "US";
    private OnItunesItemClickedListener mCallback;

    interface OnItunesItemClickedListener {
        void OnItunesItemClicked(Entry entry);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnItunesItemClickedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnItunesItemClickedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        topService = retrofit.create(ItunesTopApi.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_itunes_gallery, container, false);

        loadTopFeeds();

        // set number of rows according to orientation
        boolean isOrientationPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        int numberOfRows = isOrientationPortrait? 3 : 4;

        mRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_itunes_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfRows));

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

        return v;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
    }

    private void loadTopFeeds() {

        Call<Top> call = topService.search(countryCode, 25, isExplicit);
        call.enqueue(new Callback<Top>() {
            @Override
            public void onResponse(Call<Top> call, Response<Top> response) {

                ItunesGalleryAdapter adapter = new ItunesGalleryAdapter(getContext(), response.body());
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Top> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private class ItunesGalleryAdapter extends RecyclerView.Adapter<ItunesGalleryHolder> {
        private final Context context;
        private final Top top;

        public ItunesGalleryAdapter(Context context, Top top) {
            this.context = context;
            this.top = top;
        }



        @Override
        public ItunesGalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.gallery_item, parent, false);
            return new ItunesGalleryHolder(itemView, top);
        }

        @Override
        public void onBindViewHolder(ItunesGalleryHolder holder, int position) {
            // onBindViewHolder is called when the SO binds the view with the data -- or, in other words, the data is shown in the UI.
            Entry entry = top.getFeed().getEntry().get(position);
//            holder.title.setText(entry.getImName().getLabel());
//            holder.artist.setText(entry.getImArtist().getLabel());
            Picasso.with(context)
                    .load(entry.getImImage().get(2).getLabel())
                    // error(R.drawable.ic_broken_image_black_24dp).
                    .into(holder.mItemImageView);
        }

        @Override
        public int getItemCount() {
            return top.getFeed().getEntry().size();
        }
    }

    private class ItunesGalleryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView mItemImageView;
        final Top top;

        public ItunesGalleryHolder(View itemView, Top top) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.top = top;
            mItemImageView = (ImageView) itemView.findViewById(R.id.gallery_image_view);
        }



        @Override
        public void onClick(View view) {
            // there is no direct link to the RSS feed in top podcasts API so need to workaround
            Entry entry = top.getFeed().getEntry().get(getAdapterPosition());
            Log.d(TAG, entry.getImName().getLabel().toString());
            mCallback.OnItunesItemClicked(entry);
        }
    }
}
