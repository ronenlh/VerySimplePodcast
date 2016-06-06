package com.ronen.studio08.verysimplepodcast;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ronen.studio08.verysimplepodcast.itunestop.Entry;


public class ItunesSearchActivity extends AppCompatActivity  {

    EditText searchBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itunes_search);
        searchBox = (EditText) findViewById(R.id.searchBox);


        if(findViewById(R.id.itunes_selector_container) != null && savedInstanceState == null) {
            ItunesNavigationFragment itunesNavigationFragment = new ItunesNavigationFragment();
            getSupportFragmentManager().
                    beginTransaction().
                    add(R.id.itunes_selector_container, itunesNavigationFragment).
                    commit();
        }
    }

    public void search(View view) {
        ItunesSearchFragment itunesSearchFragment = new ItunesSearchFragment();
        Bundle args = new Bundle();
        args.putString("search", searchBox.getText().toString());
        itunesSearchFragment.setArguments(args);
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.itunes_selector_container, itunesSearchFragment).
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                addToBackStack("toSearch").
                commit();

    }

    public void search(Entry entry) {

        ItunesSearchFragment itunesSearchFragment = new ItunesSearchFragment();
        Bundle args = new Bundle();
        args.putString("search", entry.getImName().getLabel());
        itunesSearchFragment.setArguments(args);
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.itunes_selector_container, itunesSearchFragment).
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                addToBackStack("toSearch").
                commit();

    }
}
