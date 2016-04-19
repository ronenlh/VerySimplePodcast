package com.example.studio08.verysimplepodcast;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.studio08.verysimplepodcast.database.FeedReaderContract;
import com.example.studio08.verysimplepodcast.database.FeedItemsDbHelper;

import java.util.ArrayList;
import java.util.Arrays;




/**
 * Created by Ronen on 16/4/16.
 */
public class FeedSelectorFragment extends ListFragment {

    onChannelSelectedListener mCallback;

    interface onChannelSelectedListener {
        void onChannelSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (onChannelSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.feed_selector_listfragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FeedItemsDbHelper mDbHelper = new FeedItemsDbHelper(getContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String[] projection = {
                FeedReaderContract.FeedItem._ID,
                FeedReaderContract.FeedItem.COLUMN_NAME_FEED_TITLE,
                FeedReaderContract.FeedItem.COLUMN_NAME_FEED_LINK,
                FeedReaderContract.FeedItem.COLUMN_NAME_FEED_DESCRIPTION
        };
        db.query(
                FeedReaderContract.FeedItem.TABLE_FEED_NAME,    // The table to query
                projection,                                 // The columns to return
                null,                                       // The columns for the WHERE clause
                null,                                       // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                null                                        // The sort order
        );

        // sets sample array
        PodcastFeed[] podcastFeedTestArray = {};
        ArrayList<PodcastFeed> podcastFeedList = new ArrayList<>(Arrays.asList(podcastFeedTestArray));
        PodcastFeedAdapter podcastFeedAdapter = new PodcastFeedAdapter(getContext(), podcastFeedList);
        setListAdapter(podcastFeedAdapter);
//        getListView().setOnItemClickListener(this);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.d("onListItemClick", "clicked at "+ position);
        mCallback.onChannelSelected(position);
    }
}
