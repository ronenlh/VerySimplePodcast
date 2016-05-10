package com.example.studio08.verysimplepodcast;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studio08.verysimplepodcast.database.DbHelper;
import com.example.studio08.verysimplepodcast.database.FeedReaderContract;
import com.example.studio08.verysimplepodcast.database.FeedsContract;
import com.example.studio08.verysimplepodcast.retrofit.ApiService;
import com.example.studio08.verysimplepodcast.retrofit.FeedChannel;
import com.example.studio08.verysimplepodcast.retrofit.RSS;
import com.example.studio08.verysimplepodcast.retrofit.ServiceGenerator;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by studio08 on 5/10/2016.
 */
public class AddFeedFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_feed, container, false);
    }

    private void retrofitCaller(String feedUrl) {

        // database
        DbHelper mDbHelper = new DbHelper(getActivity());
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<RSS> call = service.feed(feedUrl);
        call.enqueue(new Callback<RSS>() {
            @Override
            public void onResponse(Call<RSS> call, Response<RSS> response) {
            /**  Similar to Episode Selector Fragment, but here we want info about the feed and add it to SQLite database.
                 In the FeedSelectorFragment we pull info from that Database, Feeds.db */

                RSS feed = response.body(); // <-- this is the feed!

                if (feed != null) {
                    Log.d("feed", "feed is not null: \n" + feed.toString());
                    String title = feed.getChannel().getTitle();
                    String creator = "";
                    String feedUrl = "";
                    String thumbnail = "";
                    databaseHelper(db, title, creator, feedUrl, thumbnail);

                } else
                    try {
                        Log.e("feed", response.errorBody().string());
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


    private long databaseHelper(SQLiteDatabase db, String title, String creator, String feedUrl, String thumbnail) {

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedsContract.FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(FeedsContract.FeedEntry.COLUMN_NAME_CREATOR, creator);
        values.put(FeedsContract.FeedEntry.COLUMN_NAME_FEED_URL, feedUrl);
        values.put(FeedsContract.FeedEntry.COLUMN_NAME_THUMBNAIL, thumbnail);

        // Insert the new row, returning the primary key value of the new row
//        Log.d("databaseHelper()", ""+newRowId+" "+title);
        // insertWithConflict instead of insert.
        long primaryKey = db.insertWithOnConflict(
                FeedsContract.FeedEntry.TABLE_NAME,
                FeedsContract.FeedEntry.COLUMN_NAME_NULLABLE,
                values,SQLiteDatabase.CONFLICT_IGNORE);

        return primaryKey;
    }
}
