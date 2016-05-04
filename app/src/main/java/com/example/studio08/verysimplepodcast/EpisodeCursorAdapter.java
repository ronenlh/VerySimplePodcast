package com.example.studio08.verysimplepodcast;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

import com.example.studio08.verysimplepodcast.database.FeedReaderContract;

/**
 * Created by studio08 on 5/4/2016.
 */
public class EpisodeCursorAdapter extends SimpleCursorAdapter {

    public EpisodeCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    static public EpisodeCursorAdapter EpisodeCursorAdapterFactory(Context context, Cursor c) {
        String[] from = {FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION};
        int[] to = {R.id.episode_title,
                R.id.episode_description};
        int layout = R.layout.episode_row;
        int flags = CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER;
        return new EpisodeCursorAdapter(context, layout, c, from, to, flags);
    }
}
