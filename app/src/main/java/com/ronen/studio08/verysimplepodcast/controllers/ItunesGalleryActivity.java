package com.ronen.studio08.verysimplepodcast.controllers;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.ronen.studio08.verysimplepodcast.R;
import com.ronen.studio08.verysimplepodcast.model.Converter;
import com.ronen.studio08.verysimplepodcast.model.FeedSnippet;
import com.ronen.studio08.verysimplepodcast.model.itunesNavModelClass.Entry;
import com.ronen.studio08.verysimplepodcast.model.itunesSearchModelClass.Result;

public class ItunesGalleryActivity extends SingleFragmentActivity
        implements ItunesGalleryFragment.OnItunesItemClickedListener,
        Converter.OnResultReceivedListener {

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
        // Click from Navigation on iTunes, doesn't have feed url in it, but has id in the url.
        Log.d(TAG, "OnItunesItemClicked: Entry = " + entry.getImName());

        new Converter(this).getResult(entry);
        // answer as a callback in OnResultReceived(Result)
    }

    @Override
    public void OnItunesItemClicked(Result result) {
        // Click from search results on iTunes, has feed url in it
        Log.d(TAG, "OnItunesItemClicked: Result = " + result.getCollectionName());

//        openDialog(result);
    openInfoActivity(result);

    }

    private void openInfoActivity(Result result) {
        Intent intent = new Intent(this, InfoActivity.class);
        FeedSnippet mFeedSnippet = new Converter(this).getFeedSnippet(result);
        intent.putExtra("feed", mFeedSnippet);
        startActivity(intent);
    }

    @Override
    public void OnResultReceived(Result result) {
//        openDialog(result);
        openInfoActivity(result);
    }

    private void openDialog(Result result) {
        // this calls and adds the feed: getFeedAndAddItToDb(result);
        DialogFragment dialogFragment = new ItunesDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("result", result);
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), result.toString());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.itunes_selector_container, new SettingsFragment())
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    @Override
//    public void onInfoSelected(Result result) {
//        Log.d(TAG, "onInfoSelectedListener" + result.getCollectionName());
//        String url = result.getFeedUrl();
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.setData(Uri.parse(url));
//        startActivity(i);
//    }

//    @Override
//    public void onAddSelected(Result result) {
//        getFeedAndAddItToDb(result);
//    }
//
//    private void getFeedAndAddItToDb(final Result result) {
//        ApiService service = ServiceGenerator.createService(ApiService.class);
//        Call<RSS> call = service.feed(result.getFeedUrl());
//        call.enqueue(new Callback<RSS>() {
//            @Override
//            public void onResponse(Call<RSS> call, Response<RSS> response) {
//
//                RSS rss = response.body();
//
//                if (rss != null) {
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
//                    PodcastLab.get(ItunesGalleryActivity.this).insertFeed(mFeed);
//
//                    Toast.makeText(ItunesGalleryActivity.this, "Feed Added to Playlist.", Toast.LENGTH_SHORT).show();
//                } else
//                    try {
//                        Log.e("feed", response.errorBody().string());
//                        Toast.makeText(ItunesGalleryActivity.this, R.string.error_feed, Toast.LENGTH_SHORT).show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//            }
//
//            @Override
//            public void onFailure(Call<RSS> call, Throwable t) {
//                Log.w(TAG, t);
//            }
//        });
//    }
}
