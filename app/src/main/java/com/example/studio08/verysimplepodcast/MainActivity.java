package com.example.studio08.verysimplepodcast;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.studio08.verysimplepodcast.database.FeedReaderContract;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements FeedSelectorFragment.onFeedSelectedListener,
        EpisodeSelectorFragment.onEpisodeSelectedListener, EpisodeDialogFragment.onPlaySelectedListener, EpisodeDialogFragment.onInfoSelectedListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.feed_selector_container) != null && savedInstanceState == null) {
            FeedSelectorFragment feedSelectorFragment = new FeedSelectorFragment();
            feedSelectorFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().
                    beginTransaction().
                    add(R.id.feed_selector_container, feedSelectorFragment).
                    commit();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "FeedSelector Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.studio08.verysimplepodcast/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "FeedSelector Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.studio08.verysimplepodcast/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void onFeedSelected(int position, String feedUrl){
        Toast.makeText(this, R.string.feed_selected, Toast.LENGTH_SHORT).show();
        EpisodeSelectorFragment episodeSelectorFragment = new EpisodeSelectorFragment();

        // getting and adding the feedUrl to the Fragment, this is the parameter for Retrofit.
        Bundle args = new Bundle();
        args.putString("feedUrl", feedUrl);
        episodeSelectorFragment.setArguments(args);

        // now switching Fragments
        getFragmentManager().
                beginTransaction().
                replace(R.id.feed_selector_container, episodeSelectorFragment).
                addToBackStack("toEpisode").
                commit();
    }

    @Override
    public void onEpisodeSelected(int position) {
        Toast.makeText(this, R.string.episode_selected, Toast.LENGTH_SHORT).show();
        // action in Fragment
    }

    @Override
    public void onLongEpisodeClick(int position, String url) {
        Log.d("onLongEpisodeClick", "long click");
        // nothing yet
    }

    public void addFeed(View view) {
        Toast.makeText(this, R.string.add_feed, Toast.LENGTH_SHORT).show();
        AddFeedFragment addFeedFragment = new AddFeedFragment();
        getFragmentManager().
                beginTransaction().
                replace(R.id.feed_selector_container, addFeedFragment).
                addToBackStack("toAdd").
                commit();
    }

    @Override
    public void onPlaySelected(String mediaUrl) {
        MiniPlayerFragment miniPlayerFragment = new MiniPlayerFragment();
        miniPlayerFragment.startPlayer(mediaUrl);
    }

    @Override
    public void onInfoSelected(String itemUrl) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemUrl));
        startActivity(browserIntent);
    }
}
