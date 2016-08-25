package com.ronen.studio08.verysimplepodcast.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ronen.studio08.verysimplepodcast.R;
import com.ronen.studio08.verysimplepodcast.model.itunesNavModelClass.Entry;
import com.ronen.studio08.verysimplepodcast.model.itunesNavModelClass.ItunesTopApi;
import com.ronen.studio08.verysimplepodcast.model.itunesNavModelClass.Top;
import com.ronen.studio08.verysimplepodcast.model.itunesSearchModelClass.Result;
import com.ronen.studio08.verysimplepodcast.model.itunesSearchModelClass.Search;
import com.ronen.studio08.verysimplepodcast.model.itunesSearchModelClass.SearchAPI;
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
    private SearchAPI searchService;
    private boolean isExplicit;
    private String countryCode = "US";
    private String explicitString = "No";
    private String lang = "en_us";
    private OnItunesItemClickedListener mCallback;

    interface OnItunesItemClickedListener {
        void OnItunesItemClicked(Entry entry);
        void OnItunesItemClicked(Result result);
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
        searchService = retrofit.create(SearchAPI.class);
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

        getSettings();

        return v;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchItunes(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void getSettings() {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        isExplicit = sharedPref.getBoolean(SettingsFragment.KEY_EXPLICIT, false);
        countryCode = sharedPref.getString(SettingsFragment.KEY_COUNTRY, "US");
        explicitString = sharedPref.getBoolean(SettingsFragment.KEY_EXPLICIT, false)?"Yes":"No";
        lang = sharedPref.getString(SettingsFragment.KEY_LANG, "en_us");
        SharedPreferences.OnSharedPreferenceChangeListener listener =
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                        loadTopFeeds();
                        Log.d("OnSharedPreference", key);
                    }
                };
        //   You must store a strong reference to the mCallback, or it will be susceptible to garbage collection:
        sharedPref.registerOnSharedPreferenceChangeListener(listener);
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

    private void searchItunes(String query) {
        Call<Search> call = searchService.search(countryCode, 25, query, explicitString, lang);
        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {

                Log.d("SearchAPI", response.body().toString());

                ItunesSearchAdapter adapter = new ItunesSearchAdapter(getContext(), response.body());
                adapter.setItemListener((OnItunesItemClickedListener) getActivity());
                mRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                Log.d("RetrofitCaller", t.toString());
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

            if (mCallback != null)
                mCallback.OnItunesItemClicked(entry);
        }
    }

    public class ItunesSearchAdapter extends RecyclerView.Adapter<ItunesSearchHolder>{
        Context context;
        Search search;


        public ItunesSearchAdapter(Context context, Search search) {
            this.context = context;
            this.search = search;
        }


        @Override
        public ItunesSearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.gallery_item, parent, false);
            return new ItunesSearchHolder(itemView, search);
        }

        @Override
        public void onBindViewHolder(ItunesSearchHolder holder, int position) {
            // onBindViewHolder is called when the SO binds the view with the data -- or, in other words, the data is shown in the UI.
            Result result = search.getResults().get(position);
            // is explicit enabled in settings?
//            final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
//            String name = sharedPref.getBoolean(SettingsFragment.KEY_EXPLICIT,false)?result.getCollectionName():result.getCollectionCensoredName();
//
            Picasso.with(context).
                    load(result.getArtworkUrl100())
                    // error(R.drawable.ic_broken_image_black_24dp).
                    .into(holder.mItemImageView);

        }

        @Override
        public int getItemCount() {
            return search.getResultCount();
        }

        public void setItemListener(OnItunesItemClickedListener listener) {
            mCallback = listener;
        }
    }

    public class ItunesSearchHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mItemImageView;
        Search search;

        public ItunesSearchHolder(View itemView, Search search) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.search = search;
            mItemImageView = (ImageView) itemView.findViewById(R.id.gallery_image_view);
        }

        @Override
        public void onClick(View v) {
            Result result = search.getResults().get(getAdapterPosition());
            Log.d(TAG,result.getCollectionName());

            if (mCallback != null)
                mCallback.OnItunesItemClicked(result);
        }
    }
}
