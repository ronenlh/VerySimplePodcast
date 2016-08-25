package com.ronen.studio08.verysimplepodcast.controllers;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.ronen.studio08.verysimplepodcast.R;
import com.ronen.studio08.verysimplepodcast.model.itunesNavModelClass.Entry;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public void OnItunesItemClicked(Entry entry) {

    }
}
