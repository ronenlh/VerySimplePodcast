package com.example.studio08.verysimplepodcast.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by studio08 on 4/18/2016.
 */
public class FeedItemsDbHelper extends SQLiteOpenHelper {

    //  implement methods that create and maintain the database and tables
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedItem.TABLE_FEED_NAME + " (" +
                    FeedReaderContract.FeedItem._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedItem.COLUMN_NAME_FEED_TITLE + TEXT_TYPE + COMMA_SEP +
                    FeedReaderContract.FeedItem.COLUMN_NAME_FEED_LINK + TEXT_TYPE + COMMA_SEP +
                    FeedReaderContract.FeedItem.COLUMN_NAME_FEED_DESCRIPTION + TEXT_TYPE +
                    // Any other options for the CREATE command, remember the COMMA_SEP
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedItem.TABLE_FEED_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public FeedItemsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
