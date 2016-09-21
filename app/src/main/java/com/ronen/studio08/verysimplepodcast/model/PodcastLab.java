package com.ronen.studio08.verysimplepodcast.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ronen.studio08.verysimplepodcast.model.database.DatabaseHelper;
import com.ronen.studio08.verysimplepodcast.model.database.FeedReaderContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ronen on 24/8/16.
 */

public class PodcastLab {

    private static PodcastLab mPodcastLab;
    private final String TAG = "PodcastLab";

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private DatabaseReference mFirebaseDatabaseReference;

    public static PodcastLab get(Context context) {
        if (mPodcastLab == null) {
            mPodcastLab = new PodcastLab(context);
        }
        return  mPodcastLab;
    }

    public PodcastLab(Context context) {
        this.mContext = context.getApplicationContext(); // because it is a singleton, we use the application context so we don't maintain a link to an activity than should be garbage collected.
        this.mDatabase = new DatabaseHelper(mContext).getWritableDatabase();
        // Write a message to the database
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("Feeds");
    }

    public Cursor queryFeeds(String selection, String[] selectionArgs) {

        // Defines a projection that specifies which columns from the database
        // you will actually use after this query.
//       String[] projection = {
//                FeedReaderContract.Feed._ID,                    // 0
//                FeedReaderContract.Feed.COLUMN_NAME_TITLE,      // 1
//                FeedReaderContract.Feed.COLUMN_NAME_CREATOR,    // 2
//                FeedReaderContract.Feed.COLUMN_NAME_FEED_URL,   // 3
//                FeedReaderContract.Feed.COLUMN_NAME_SUBTITLE,   // 4
//                FeedReaderContract.Feed.COLUMN_NAME_THUMBNAIL   // 5
//        };
/*
        // How you want the results sorted in the resulting Cursor
        String sortOrder = FeedReaderContract.Feed.COLUMN_NAME_TITLE;

        return new FeedSnippetCursorWrapper(mDatabase.query(
                FeedReaderContract.Feed.TABLE_NAME,     // The table to query
                projection,                             // The columns to return
                null,                                   // The columns for the WHERE clause
                null,                                   // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                sortOrder                               // The sort order
        ));*/

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(FeedReaderContract.Feed.TABLE_NAME);
        DatabaseHelper mDatabaseOpenHelper = new DatabaseHelper(mContext);

        Cursor cursor = builder.query(mDatabaseOpenHelper.getReadableDatabase(),
                null,
                selection,
                selectionArgs,
                null,
                null,
                FeedReaderContract.Feed.COLUMN_NAME_TITLE);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            // cursor is empty
            cursor.close();
            return null;
        }
        return cursor;
    }

    public void deleteFeed(String feedId) {
            // Define 'where' part of query.
            String selection = FeedReaderContract.Feed._ID + " LIKE ?";

            // Specify arguments in placeholder order.
            String[] selectionArgs = {feedId};

            // Issue SQL statement.
            mDatabase.delete(FeedReaderContract.Feed.TABLE_NAME, selection, selectionArgs);

//        String deleteQuery = "DELETE FROM  " + FeedsContract.FeedEntry.TABLE_NAME + " where " + FeedsContract.FeedEntry._ID + "='" + feedId + "'";
//        Log.d("Query", deleteQuery);
//        mDatabase.execSQL(deleteQuery);
    }


    public long insertFeed(FeedSnippet feed) {

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(FeedReaderContract.Feed.COLUMN_NAME_TITLE, feed.getTitle());
            values.put(FeedReaderContract.Feed.COLUMN_NAME_CREATOR, feed.getCreator());
            values.put(FeedReaderContract.Feed.COLUMN_NAME_FEED_URL, feed.getFeedUrl());
            values.put(FeedReaderContract.Feed.COLUMN_NAME_SUBTITLE, feed.getSubtitle());
            values.put(FeedReaderContract.Feed.COLUMN_NAME_UUID, feed.getUUID().toString());
            values.put(FeedReaderContract.Feed.COLUMN_NAME_TIME_ADDED, feed.getTimeAdded());
            values.put(FeedReaderContract.Feed.COLUMN_NAME_THUMBNAIL, feed.getThumbnail());

            // Insert the new row, returning the primary key value of the new row
            Log.d(TAG, feed.getTitle());
            // insertWithConflict instead of insert.

            return mDatabase.insertWithOnConflict(
                    FeedReaderContract.Feed.TABLE_NAME,
                    FeedReaderContract.Feed.COLUMN_NAME_NULLABLE,
                    values,
                    SQLiteDatabase.CONFLICT_IGNORE);

    }
}
