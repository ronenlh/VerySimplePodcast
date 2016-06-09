package com.ronen.studio08.verysimplepodcast;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ronen.studio08.verysimplepodcast.database.DbHelper;
import com.ronen.studio08.verysimplepodcast.database.FeedsContract;
import com.ronen.studio08.verysimplepodcast.itunes.Result;
import com.ronen.studio08.verysimplepodcast.itunestop.Entry;
import com.ronen.studio08.verysimplepodcast.retrofit.ApiService;
import com.ronen.studio08.verysimplepodcast.retrofit.Channel;
import com.ronen.studio08.verysimplepodcast.retrofit.RSS;
import com.ronen.studio08.verysimplepodcast.retrofit.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ItunesSearchActivity extends AppCompatActivity  implements ItunesSearchFragment.OnSearchItemSelectedListener{

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
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

    public void retrofitCaller(final Result result) {
        ApiService service = ServiceGenerator.createService(ApiService.class);
        DbHelper dbHelper = new DbHelper(this);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        Call<RSS> call = service.feed(result.getFeedUrl());
        call.enqueue(new Callback<RSS>() {
            @Override
            public void onResponse(Call<RSS> call, Response<RSS> response) {
                /**  Similar to Episode Selector Fragment, but here we want info about the feed and add it to SQLite database.
                 In the FeedSelectorFragment we pull info from that Database, Feeds.db */

                RSS rss = response.body(); // <-- this is the feed!

                if (rss != null) {
                    Log.d("feed", "feed is not null: \n" + rss.toString());

                    Channel channel = rss.getChannel();
                    String feedUrl = result.getFeedUrl();
                    String title = channel.getTitle();
                    String creator;
                    if ((creator = channel.getAuthor()) == null)
                        creator = channel.getItemList().get(0).getAuthorList().get(0);
                    String subtitle = channel.getSubtitle();
                    String thumbnail = channel.getImage();
                    databaseHelper(db, title, creator, feedUrl, subtitle, thumbnail);

                } else
                    try {
                        Log.e("feed", response.errorBody().string());
                        Toast.makeText(ItunesSearchActivity.this, R.string.error_feed, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            public void onFailure(Call<RSS> call, Throwable t) {
                Log.d("failure", t.toString());
            }
        });
    }

    private long databaseHelper(SQLiteDatabase db, String title, String creator, String feedUrl, String subtitle, String thumbnail) {

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedsContract.FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(FeedsContract.FeedEntry.COLUMN_NAME_CREATOR, creator);
        values.put(FeedsContract.FeedEntry.COLUMN_NAME_FEED_URL, feedUrl);
        values.put(FeedsContract.FeedEntry.COLUMN_NAME_SUBTITLE, subtitle);
        values.put(FeedsContract.FeedEntry.COLUMN_NAME_THUMBNAIL, thumbnail);

        // Insert the new row, returning the primary key value of the new row
        Log.d("databaseHelper()", title);
        // insertWithConflict instead of insert.
        long primaryKey = db.insertWithOnConflict(
                FeedsContract.FeedEntry.TABLE_NAME,
                FeedsContract.FeedEntry.COLUMN_NAME_NULLABLE,
                values, SQLiteDatabase.CONFLICT_IGNORE);

        return primaryKey;
    }

    @Override
    public void onItemSelected(Result result) {
        retrofitCaller(result);
    }
}