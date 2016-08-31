package com.ronen.studio08.verysimplepodcast.model;

import android.util.Log;

import com.ronen.studio08.verysimplepodcast.model.itunesNavModelClass.Entry;
import com.ronen.studio08.verysimplepodcast.model.retrofit.LookupAPI;
import com.ronen.studio08.verysimplepodcast.model.itunesSearchModelClass.Result;
import com.ronen.studio08.verysimplepodcast.model.itunesSearchModelClass.Search;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ronen on 30/8/16.
 */

public class EntryToResultConverter {
    private final String TAG = "EntryToResultConverter";
    private static EntryToResultConverter sharedInstance;
    OnResultReceivedListener mCallback;

    public static EntryToResultConverter get(OnResultReceivedListener mCallback) {
        if (sharedInstance == null)
            sharedInstance = new EntryToResultConverter(mCallback);
        return sharedInstance;
    }

    private EntryToResultConverter(OnResultReceivedListener mCallback) {
        this.mCallback = mCallback;
    }

    public interface OnResultReceivedListener {
        void OnResultReceived(Result result);
    }

    /*  Extract the Id from the link with a regex or something else
        EX: https://itunes.apple.com/podcast/state-trance-official-podcast/id260190086
        load this link with the ID https://itunes.apple.com/lookup?id=260190086&entity=podcast
        Get the "feedUrl" from this json file.
        The &entity=podcast is not necessary but it may help */

    public Result convert(Entry entry) {
        String id;

        // 1. Extract the Id from the link with a regex or something else
        String feedUrl = entry.getLink().getAttributes().getHref();
        Pattern pattern = Pattern.compile("id(\\d*)");
        Matcher matcher = pattern.matcher(feedUrl);
        if (matcher.find()) {
            id = matcher.group(1);
            Log.d(TAG, "RegEx returned: " + id);

            // 2. Load this link with the ID https://itunes.apple.com/lookup?id=260190086&entity=podcast
            retrofitCaller(id);
        }

        Log.d(TAG, "RegEx didn't find a match");
        return null;
    }

    private void retrofitCaller(String id) {
        final Result[] result = new Result[1];

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LookupAPI lookupService = retrofit.create(LookupAPI.class);

        Call<Search> call = lookupService.search(id);
        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {

                Log.d(TAG, "LookupAPI response: " + response.body().toString());

                if (!response.body().getResults().isEmpty()) {
                    Log.d(TAG, "LookupAPI name: " + response.body().getResults().get(0).getCollectionName());

                    mCallback.OnResultReceived(response.body().getResults().get(0));
                } else {
                    Log.d(TAG, "onResponse: getResults() is empty");
                }
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                Log.w(TAG, t);
            }
        });
    }
}
