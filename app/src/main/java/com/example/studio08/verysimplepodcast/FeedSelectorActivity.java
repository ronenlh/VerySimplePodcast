package com.example.studio08.verysimplepodcast;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.studio08.verysimplepodcast.database.FeedReaderContract;
import com.example.studio08.verysimplepodcast.database.FeedReaderDbHelper;
import com.example.studio08.verysimplepodcast.retrofit.FeedChannel;
import com.example.studio08.verysimplepodcast.retrofit.RSS;
import com.example.studio08.verysimplepodcast.retrofit.ApiService;
import com.example.studio08.verysimplepodcast.retrofit.ServiceGenerator;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedSelectorActivity extends AppCompatActivity implements FeedSelectorFragment.onChannelSelectedListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_selector);

        startBar();
        simpleXML();
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

    public void onChannelSelected(int position){
        ChannelListFragment channelListFragment = new ChannelListFragment();
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.feed_selector_container, channelListFragment).
                addToBackStack(null).
                commit();
    }


    private void startBar() {
        ImageView plusButton = (ImageView) findViewById(R.id.plus_imageView);
        if (plusButton != null) {
            plusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(FeedSelectorActivity.this, AddPodcastActivity.class));
                }
            });
        }
    }

    private void simpleXML() {

        // database
        FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(this);
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<RSS> call = service.feed("serialpodcast");
        call.enqueue(new Callback<RSS>() {
            @Override
            public void onResponse(Call<RSS> call, Response<RSS> response) {
                RSS feed = response.body();
                if (feed != null) {
                    Log.d("feed", "feed is not null: \t" + feed.toString());
                    // first:
                    for (FeedChannel.Item item : feed.getChannel().itemList) {
                        String title = item.title;
                        String link = item.link;
                        String description = item.description;
                        String url = item.enclosure.url;
                        databaseHelper(db, title, link, description, url);
                    }
                    // after that:
                    Cursor cursor = cursor(db);
                    ChannelListFragment channelListFragment = new ChannelListFragment();

                    // stuff needed to initialize ChannelCursorAdapter
                    String[] from = {FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                            FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION};
                    int[] to = {R.id.episode_title,
                            R.id.episode_description};
                    ChannelCursorAdapter adapter = new ChannelCursorAdapter(FeedSelectorActivity.this,
                            R.layout.channel_row,
                            cursor,
                            from,
                            to,
                            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                    channelListFragment.setListAdapter(adapter);
                } else
                    try {
                        Log.d("feed", response.errorBody().string());
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

    private long databaseHelper(SQLiteDatabase db, String title, String link, String description, String url) {

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LINK, link);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, description);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_URL, url);

        // Insert the new row, returning the primary key value of the new row
//        Log.d("databaseHelper()", ""+newRowId+" "+title);
        return db.insert(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                FeedReaderContract.FeedEntry.COLUMN_NAME_NULLABLE,
                values);
    }

    private Cursor cursor(SQLiteDatabase db) {

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_LINK,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION,
                FeedReaderContract.FeedEntry.COLUMN_NAME_URL
        };

        // How you want the results sorted in the resulting Cursor
//        String sortOrder =
//                FeedReaderContract.FeedEntry.COLUMN_NAME_UPDATED + " DESC";

        return db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
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
}
