package com.example.studio08.verysimplepodcast;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.studio08.verysimplepodcast.database.FeedReaderContract;

/**
 * Created by studio08 on 4/18/2016.
 */
public class ChannelCursorAdapter extends CursorAdapter {
    public ChannelCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public ChannelCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.channel_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView episodeTitle = (TextView) view.findViewById(R.id.episode_title);
        TextView episodeDescription = (TextView) view.findViewById(R.id.episode_description);

        String title = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE));
        String description = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION));

        episodeTitle.setText(title);
        episodeDescription.setText(description);
    }
}
