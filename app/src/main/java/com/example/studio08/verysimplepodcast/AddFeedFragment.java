package com.example.studio08.verysimplepodcast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.studio08.verysimplepodcast.database.DbHelper;
import com.example.studio08.verysimplepodcast.database.FeedReaderContract;
import com.example.studio08.verysimplepodcast.database.FeedsContract;
import com.example.studio08.verysimplepodcast.retrofit.ApiService;
import com.example.studio08.verysimplepodcast.retrofit.FeedChannel;
import com.example.studio08.verysimplepodcast.retrofit.RSS;
import com.example.studio08.verysimplepodcast.retrofit.ServiceGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/**
 * Created by studio08 on 5/10/2016.
 */
public class AddFeedFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    View view;
    List<Feed> sampleFeedList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_feed, container, false);
        Button button = (Button) view.findViewById(R.id.add_button);
        button.setOnClickListener(this);

        // sample Feed List
        sampleFeedList = new ArrayList<>();
        sampleFeedList.add(new Feed("99% Invisible","http://feeds.feedburner.com/99pi?format=xml"));
        sampleFeedList.add(new Feed("Serial","http://feeds.serialpodcast.org/serialpodcast"));
        sampleFeedList.add(new Feed("Fragmented","https://simplecast.com/podcasts/1684/rss"));
        sampleFeedList.add(new Feed("podCast 411","http://www.podcast411.com/new_demo_feed.xml"));
        ArrayAdapter<Feed> adapter = new ArrayAdapter<Feed>(getContext(),android.R.layout.simple_list_item_1,sampleFeedList);
        ListView listView = (ListView) view.findViewById(R.id.sample_feeds);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        return view;
    }

    private void retrofitCaller(final String feedUrl) {

        // database
        DbHelper dbHelper = new DbHelper(getActivity());
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<RSS> call = service.feed(feedUrl);
        call.enqueue(new Callback<RSS>() {
            @Override
            public void onResponse(Call<RSS> call, Response<RSS> response) {
            /**  Similar to Episode Selector Fragment, but here we want info about the feed and add it to SQLite database.
                 In the FeedSelectorFragment we pull info from that Database, Feeds.db */

                RSS feedChannel = response.body(); // <-- this is the feed!

                if (feedChannel != null) {
                    Log.d("feed", "feed is not null: \n" + feedChannel.toString());
                    String title = feedChannel.getChannel().getTitle();
                    String creator = "";
                    if ((creator = feedChannel.getChannel().getAuthor()) == null)
                        creator = feedChannel.getChannel().getItemList().get(0).getAuthor();
                    String subtitle = feedChannel.getChannel().getSubtitle();
                    String thumbnail = feedChannel.getChannel().getImage();
                    databaseHelper(db, title, creator, feedUrl, subtitle, thumbnail);

                } else
                    try {
                        Log.e("feed", response.errorBody().string());
                        Toast.makeText(getActivity(), R.string.error_feed, Toast.LENGTH_SHORT).show();
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
                values,SQLiteDatabase.CONFLICT_IGNORE);

        return primaryKey;
    }

    @Override
    public void onClick(View v) {
        EditText editText = (EditText) view.findViewById(R.id.feed_editText);
        retrofitCaller(editText.getText().toString());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        retrofitCaller(sampleFeedList.get(position).getFeedUrl());
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}
