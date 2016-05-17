package com.example.studio08.verysimplepodcast;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

import com.example.studio08.verysimplepodcast.database.FeedReaderContract;
import com.example.studio08.verysimplepodcast.database.FeedsContract;
import com.squareup.picasso.Picasso;

/**
 * Created by studio08 on 5/4/2016.
 */
public class FeedCursorAdapter extends SimpleCursorAdapter {

    public FeedCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    static public FeedCursorAdapter FeedCursorAdapterFactory(Context context, Cursor c) {
        String[] from = {FeedsContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedsContract.FeedEntry.COLUMN_NAME_CREATOR};
        int[] to = {R.id.title_textView,
                R.id.creator_textView};
        int layout = R.layout.main_row;
        int flags = CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER;
        return new FeedCursorAdapter(context, layout, c, from, to, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        ImageView imageView = (ImageView) view.findViewById(R.id.thumbnail_imageView);
        Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return super.newView(context, cursor, parent);
    }
}
