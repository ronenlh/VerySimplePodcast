package com.example.studio08.verysimplepodcast;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by studio08 on 4/18/2016.
 */
public class ChannelCursorAdapter extends SimpleCursorAdapter {

    public ChannelCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.channel_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
//        TextView episodeTitle = (TextView) view.findViewById();
//        TextView episodeDescription = (TextView) view.findViewById();
//
//        String title = cursor.getString(cursor.getColumnIndexOrThrow());
//        String description = cursor.getString(cursor.getColumnIndexOrThrow());
//
//        episodeTitle.setText(title);
//        episodeDescription.setText(description);
    }
}
