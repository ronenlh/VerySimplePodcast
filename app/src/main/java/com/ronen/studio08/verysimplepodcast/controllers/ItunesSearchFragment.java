package com.ronen.studio08.verysimplepodcast.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ronen.studio08.verysimplepodcast.R;
import com.ronen.studio08.verysimplepodcast.model.itunesSearchModelClass.Result;
import com.ronen.studio08.verysimplepodcast.model.itunesSearchModelClass.Search;
import com.ronen.studio08.verysimplepodcast.model.retrofit.SearchAPI;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ronen on 5/6/16.
 */
public class ItunesSearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchAPI searchService;
    private View view;
    private String countryCode = "US";
    private String explicitString = "No";
    private String lang = "en_us";

    public interface OnSearchItemSelectedListener{
        void onItemSelected(Result result);
    }

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        explicitString = sharedPref.getBoolean(SettingsFragment.KEY_EXPLICIT, false)?"Yes":"No";
        countryCode = sharedPref.getString(SettingsFragment.KEY_COUNTRY, "US");
        lang = sharedPref.getString(SettingsFragment.KEY_LANG, "en_us");
        SharedPreferences.OnSharedPreferenceChangeListener listener =
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                        retrofitCallerAndSetAdapter();
                        Log.d("OnSharedPreference", key);
                    }
                };
        //   You must store a strong reference to the mCallback, or it will be susceptible to garbage collection:
        sharedPref.registerOnSharedPreferenceChangeListener(listener);

        retrofitCallerAndSetAdapter();
        return view;
    }

    private void retrofitCallerAndSetAdapter() {
        Call<Search> call = searchService.search(countryCode, 25, getArguments().getString("search"), explicitString, lang);
        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {

                Log.d("SearchAPI", response.body().toString());

                ItunesSearchAdapter adapter = new ItunesSearchAdapter(getContext(), response.body());
                adapter.setItemListener((OnSearchItemSelectedListener) getActivity());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                Log.d("RetrofitCaller", t.toString());
            }
        });
    }

    OnSearchItemSelectedListener onSearchItemSelectedListener;

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView thumbnail;
        final TextView title;
        final TextView artist;
        final Search search;
//        protected Context context;

        public SearchViewHolder(View itemView, Search search /*, Context context */) {
            super(itemView);
            itemView.setOnClickListener(this);
            view = itemView;
            this.search = search;
//            this.context = context;
            thumbnail = (ImageView) itemView.findViewById(R.id.add_thumbnail);
            title = (TextView) itemView.findViewById(R.id.podTitle);
            artist = (TextView) itemView.findViewById(R.id.podArtist);
        }

        @Override
        public void onClick(View v) {
            Result result = search.getResults().get(getAdapterPosition());
            Log.d("ItunesSearchHolder",result.getCollectionName());

            if (onSearchItemSelectedListener != null) {
                onSearchItemSelectedListener.onItemSelected(result);
            }

//            ItunesActivity itunesSearchActivity = new ItunesActivity();
//            itunesSearchActivity.retrofitCallerAndSetAdapter(result);
//            if(context instanceof ItunesActivity){
//                ((ItunesActivity)context).retrofitCallerAndSetAdapter(result);
//            }


        }
    }

    public class ItunesSearchAdapter extends RecyclerView.Adapter<SearchViewHolder>{
        private final Context context;
        private final Search search;


        public ItunesSearchAdapter(Context context, Search search) {
            this.context = context;
            this.search = search;
        }


        @Override
        public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.itunes_feed_row_card, parent, false);
            return new SearchViewHolder(itemView, search /*, context */);
        }

        @Override
        public void onBindViewHolder(SearchViewHolder holder, int position) {
            // onBindViewHolder is called when the SO binds the view with the data -- or, in other words, the data is shown in the UI.
            Result result = search.getResults().get(position);
            // is explicit enabled in settings?
            final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            String name = sharedPref.getBoolean(SettingsFragment.KEY_EXPLICIT,false)?result.getCollectionName():result.getCollectionCensoredName();
            holder.title.setText(name);

            holder.artist.setText(result.getArtistName());
            Picasso.with(context).
                    load(search.getResults().get(position).getArtworkUrl100()).
                    // transform(new CropSquareTransformation()).
                    // error(R.drawable.ic_broken_image_black_24dp).
                            into(holder.thumbnail, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
//                        view.findViewById(R.id.RVprogressBar).setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError() {

                        }
                    });

        }

        @Override
        public int getItemCount() {
            return search.getResultCount();
        }

        public void setItemListener(ItunesSearchFragment.OnSearchItemSelectedListener listener) {
            onSearchItemSelectedListener = listener;
        }
    }


}
