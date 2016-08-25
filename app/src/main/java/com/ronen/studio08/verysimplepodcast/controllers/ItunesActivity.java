//package com.ronen.studio08.verysimplepodcast.controllers;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.support.v4.app.DialogFragment;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.ronen.studio08.verysimplepodcast.R;
//import com.ronen.studio08.verysimplepodcast.model.FeedSnippet;
//import com.ronen.studio08.verysimplepodcast.model.PodcastLab;
//import com.ronen.studio08.verysimplepodcast.model.itunesSearchModelClass.Result;
//import com.ronen.studio08.verysimplepodcast.model.itunesNavModelClass.Entry;
//import com.ronen.studio08.verysimplepodcast.model.retrofit.ApiService;
//import com.ronen.studio08.verysimplepodcast.model.retrofit.Channel;
//import com.ronen.studio08.verysimplepodcast.model.retrofit.RSS;
//import com.ronen.studio08.verysimplepodcast.model.retrofit.ServiceGenerator;
//
//import java.io.IOException;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//
//public class ItunesActivity extends AppCompatActivity
//        implements ItunesSearchFragment.OnSearchItemSelectedListener,
//        ItunesNavigationFragment.OnNavigationItemClickedListener,
//        ItunesDialogFragment.onPlaySelectedListener,
//        ItunesDialogFragment.onInfoSelectedListener {
//
//    // class to delete
//
//    private static final String TAG = "ItunesActivity";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_itunes_search);
//        Log.d(TAG, "onCreate()");
//
//        if(findViewById(R.id.itunes_selector_container) != null && savedInstanceState == null) {
//            ItunesNavigationFragment itunesNavigationFragment = new ItunesNavigationFragment();
//            getSupportFragmentManager().
//                    beginTransaction().
//                    add(R.id.itunes_selector_container, itunesNavigationFragment).
//                    commit();
//        }
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.search_menu, menu);
//        return true;
//    }
//
//    EditText searchBox;
//
//    public void search(View view) {
//        Bundle args = new Bundle();
//        if (searchBox == null)
//            searchBox = (EditText) findViewById(R.id.searchBox);
//        args.putString("search", searchBox.getText().toString());
//        search(args);
//    }
//
//    public void search(Entry entry) {
//        Bundle args = new Bundle();
//        args.putString("search", entry.getImName().getLabel());
//        search(args);
//    }
//
//
//    public void search(Bundle args) {
//        ItunesSearchFragment itunesSearchFragment = new ItunesSearchFragment();
//        itunesSearchFragment.setArguments(args);
//        getSupportFragmentManager().
//                beginTransaction().
//                replace(R.id.itunes_selector_container, itunesSearchFragment).
//                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
//                addToBackStack("toSearch").
//                commit();
//    }
//
//
//    private void getFeedAndAddItToDb(final Result result) {
//        ApiService service = ServiceGenerator.createService(ApiService.class);
//        Call<RSS> call = service.feed(result.getFeedUrl());
//        call.enqueue(new Callback<RSS>() {
//            @Override
//            public void onResponse(Call<RSS> call, Response<RSS> response) {
//*  Similar to Episode Selector Fragment, but here we want info about the feed and add it to SQLite database.
//                 In the FeedSelectorFragment we pull info from that Database, Feeds.db
//
//
//                RSS rss = response.body(); // <-- this is the feed!
//
//                if (rss != null) {
//                    Log.d("feed", "feed is not null: \n" + rss.toString());
//
//                    Channel channel = rss.getChannel();
//
//                    FeedSnippet mFeed = new FeedSnippet();
//
//                    mFeed.setFeedUrl(result.getFeedUrl());
//                    mFeed.setTitle(channel.getTitle());
//                    String creator = (channel.getAuthor() != null) ? channel.getAuthor() :
//                        channel.getItemList().get(0).getAuthorList().get(0);
//                    mFeed.setCreator(creator);
//                    mFeed.setSubtitle(channel.getSubtitle());
//                    mFeed.setThumbnail(channel.getImage());
//
//                    PodcastLab.get(ItunesActivity.this).insertFeed(mFeed);
//
//                    Toast.makeText(ItunesActivity.this, "Feed Added to Playlist.", Toast.LENGTH_SHORT).show();
//                } else
//                    try {
//                        Log.e("feed", response.errorBody().string());
//                        Toast.makeText(ItunesActivity.this, R.string.error_feed, Toast.LENGTH_SHORT).show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//            }
//
//            @Override
//            public void onFailure(Call<RSS> call, Throwable t) {
//                Log.d("failure", t.toString());
//            }
//        });
//    }
//
//
//
//    @Override
//    public void onItemSelected(Result result) {
//
//        DialogFragment dialogFragment = new ItunesDialogFragment();
//        Bundle args = new Bundle();
//        args.putSerializable("result", result);
//        dialogFragment.setArguments(args);
//        dialogFragment.show(getSupportFragmentManager(), result.getCollectionId().toString());
//
//        // this calls and adds the feed: getFeedAndAddItToDb(result);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.settings:
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.itunes_selector_container, new SettingsFragment())
//                        .commit();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d(TAG, "onPause()");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.d(TAG, "onResume()");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d(TAG, "onDestroy()");
//    }
//
//    @Override
//    public void OnNavigationItemClicked(Entry entry) {
//        search(entry);
//    }
//
//    @Override
//    public void onAddSelected(Result result) {
//        getFeedAndAddItToDb(result);
//        Log.d(TAG, "onPlaySelectedListener: " + result.getCollectionName());
//
//        finish();
//    }
//
//    @Override
//    public void onInfoSelected(Result result) {
//
//        Log.d(TAG, "onInfoSelectedListener" + result.getCollectionName());
//        String url = result.getFeedUrl();
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.setData(Uri.parse(url));
//        startActivity(i);
//    }
//}
