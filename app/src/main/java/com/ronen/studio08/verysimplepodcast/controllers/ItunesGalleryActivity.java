package com.ronen.studio08.verysimplepodcast.controllers;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.ronen.studio08.verysimplepodcast.R;
import com.ronen.studio08.verysimplepodcast.model.itunesNavModelClass.Entry;
import com.ronen.studio08.verysimplepodcast.model.itunesSearchModelClass.Result;

public class ItunesGalleryActivity extends SingleFragmentActivity
        implements ItunesGalleryFragment.OnItunesItemClickedListener {

    private static final String TAG = "ItunesGalleryActivity";

    @Override
    protected Fragment createFragment() {
        return new ItunesGalleryFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
    }


    @Override
    public void OnItunesItemClicked(Entry entry) {
        Log.d(TAG, "OnItunesItemClicked: " + entry.getImName());
    }

    @Override
    public void OnItunesItemClicked(Result result) {
        Log.d(TAG, "OnItunesItemClicked: " + result.getCollectionName());
    }
}
