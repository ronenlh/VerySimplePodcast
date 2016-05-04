package com.example.studio08.verysimplepodcast;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

import com.example.studio08.verysimplepodcast.database.FeedReaderContract;

/**
 * Created by studio08 on 5/4/2016.
 */
public class PodcastFeedCursorAdapter extends SimpleCursorAdapter {

    public PodcastFeedCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    static public PodcastFeedCursorAdapter PodcastFeedCursorAdapterFactory(Context context, Cursor c) {
        String[] from = {FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION};
        int[] to = {R.id.episode_title,
                R.id.episode_description};
        int layout = R.layout.channel_row;
        int flags = CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER;
        return new PodcastFeedCursorAdapter(context, layout, c, from, to, flags);
    }
}
