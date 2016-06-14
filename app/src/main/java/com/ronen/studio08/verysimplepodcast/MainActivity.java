package com.ronen.studio08.verysimplepodcast;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity implements FeedSelectorFragment.onFeedSelectedListener,
        EpisodeSelectorFragment.onEpisodeSelectedListener,
        EpisodeDialogFragment.onPlaySelectedListener,
        EpisodeDialogFragment.onInfoSelectedListener,
        FeedSelectorFragment.feedDeletedListener {

    private static final String TAG = "Main Activity";

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "InstanceID token: " + FirebaseInstanceId.getInstance().getToken());

        if(findViewById(R.id.feed_selector_container) != null && savedInstanceState == null) {
            FeedSelectorFragment feedSelectorFragment = new FeedSelectorFragment();
            feedSelectorFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().
                    beginTransaction().
                    add(R.id.feed_selector_container, feedSelectorFragment).
                    commit();
        }

        // trying to display the logo in the ActionBar, not seeing any results.
        ActionBar ab;
        if ((ab = getSupportActionBar()) != null)
            ab.setDisplayUseLogoEnabled(true);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "FeedSample Selector", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
//                Uri.parse(null),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.ronen.studio08.verysimplepodcast/http/host/path")
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
                Uri.parse("android-app://com.ronen.studio08.verysimplepodcast/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void onFeedSelected(int position, String feedUrl){
        Log.d(TAG, "feed selected");
        EpisodeSelectorFragment episodeSelectorFragment = new EpisodeSelectorFragment();

        // getting and adding the feedUrl to the Fragment, this is the parameter for Retrofit.
        Bundle args = new Bundle();
        args.putString("feedUrl", feedUrl);
        episodeSelectorFragment.setArguments(args);

        // now switching Fragments
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.feed_selector_container, episodeSelectorFragment).
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                    addToBackStack("toEpisode").
                    commit();
        } else {
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.episode_selector_container, episodeSelectorFragment).
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                    commit();
        }
    }

    @Override
    public void onEpisodeSelected(int position) {
       Log.d(TAG, "episode selected");
        // action in Fragment
    }

    @Override
    public void onLongEpisodeClick(int position, String url) {
        Log.d("onLongEpisodeClick", "long click");
        // nothing yet
    }

    public void addFeed(View view) {
        Log.d(TAG, "add feed");
/*        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            AddFeedFragment addFeedFragment = new AddFeedFragment();
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.feed_selector_container, addFeedFragment).
                    addToBackStack("toAdd").
                    commit();
        } else {
            AddFeedFragment addFeedFragment = new AddFeedFragment();
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.episode_selector_container, addFeedFragment).
                    addToBackStack("toAdd").
                    commit();
        }*/

        // substituted the Fragment above for an Activity
        Intent intent = new Intent(this, ItunesSearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPlaySelected(String mediaUrl) {
//        MiniPlayerFragment miniPlayerFragment = new MiniPlayerFragment();
        MiniPlayerFragment miniPlayerFragment = (MiniPlayerFragment) getSupportFragmentManager().findFragmentById(R.id.miniplayer_fragment);
        miniPlayerFragment.startPlayer(mediaUrl);
    }

    @Override
    public void onInfoSelected(String itemUrl) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemUrl));
        startActivity(browserIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.feed_selector_container, new SettingsFragment())
                            .commit();
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.episode_selector_container, new SettingsFragment())
                            .commit();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void feedDeleted() {
        // UI feedback
        final CoordinatorLayout coordinatorLayout;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        }
        else {
            coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_land);
        }
        Snackbar snackbar = null;
        if (coordinatorLayout != null) {
            snackbar = Snackbar
                    .make(coordinatorLayout, R.string.feed_deleted, Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Snackbar
                                    .make(coordinatorLayout, R.string.feed_restored, Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    });
        }
        snackbar.show();
    }
}
