package com.example.studio08.verysimplepodcast;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.studio08.verysimplepodcast.database.FeedReaderContract;

/**
 * Created by studio08 on 4/18/2016.
 */
public class ChannelCursorAdapter extends SimpleCursorAdapter {

    public ChannelCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }
}
