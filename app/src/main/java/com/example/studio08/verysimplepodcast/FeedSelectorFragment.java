package com.example.studio08.verysimplepodcast;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.studio08.verysimplepodcast.database.DatabaseHelper;
import com.example.studio08.verysimplepodcast.database.DbHelper;
import com.example.studio08.verysimplepodcast.database.FeedReaderContract;
import com.example.studio08.verysimplepodcast.database.FeedsContract;

import java.util.ArrayList;


/**
 * Created by Ronen on 16/4/16.
 */
public class FeedSelectorFragment extends ListFragment {

    ArrayList<Feed> podcastFeedList;
    onFeedSelectedListener mCallback;

    interface onFeedSelectedListener {
        void onFeedSelected(int position, String feedUrl);
    }

    @Override
    public void onAttach(Context context) {
        // To allow a Fragment to communicate up to its Activity, you can define an interface in the Fragment class and implement it within the Activity.
        // The Fragment captures the interface implementation during its onAttach() lifecycle method and can then call the Interface methods in order to communicate with the Activity.
        super.onAttach(context);
        try {
            mCallback = (onFeedSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onEpisodeSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.feed_selector_listfragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // sets sample array
//        podcastFeedList = new ArrayList<>();
//        for (int i = 1; i < 20; i++) {
//            podcastFeedList.add(new PodcastFeed("Sample Podcast " + i));
//        }
//        podcastFeedList.add(new Feed("Serial", "http://feeds.serialpodcast.org/serialpodcast"));
//        podcastFeedList.add(new Feed("99% Invisible", "https://feeds.feedburner.com/99pi"));

//        FeedBaseAdapter adapter = new FeedBaseAdapter(getContext(), podcastFeedList);

        DbHelper dbHelper = new DbHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = cursor(db);
        FeedCursorAdapter adapter = FeedCursorAdapter.FeedCursorAdapterFactory(getContext(), cursor);

//        ArrayAdapter<PodcastFeed> adapter = new ArrayAdapter<PodcastFeed>(getContext(),android.R.layout.simple_list_item_1,podcastFeedList);
        setListAdapter(adapter);

    }

    private Cursor cursor(SQLiteDatabase db) {

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedsContract.FeedEntry._ID,
                FeedsContract.FeedEntry.COLUMN_NAME_TITLE, // 1
                FeedsContract.FeedEntry.COLUMN_NAME_CREATOR, // 2
                FeedsContract.FeedEntry.COLUMN_NAME_FEED_URL // 3
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = null; //FeedReaderContract.FeedEntry.TABLE_NAME + " DESC";

        return db.query(
                FeedsContract.FeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Cursor cursor = (Cursor) l.getItemAtPosition(position);
        Log.d("onListItemClick", cursor.getString(3));
        mCallback.onFeedSelected(position, cursor.getString(3));
        super.onListItemClick(l, v, position, id);
    }
}
