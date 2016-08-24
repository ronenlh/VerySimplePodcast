package com.ronen.studio08.verysimplepodcast.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by studio08 on 4/18/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    //  implement methods that create and maintain the database and tables
    private static final String TEXT_TYPE = " TEXT ";
    private static final String COMMA_SEP = " , ";
/*    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.Entry.TABLE_NAME + " (" +
                    FeedReaderContract.Entry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.Entry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    FeedReaderContract.Entry.COLUMN_NAME_LINK + TEXT_TYPE + " UNIQUE " + COMMA_SEP +
                    FeedReaderContract.Entry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    FeedReaderContract.Entry.COLUMN_NAME_MEDIA_URL + TEXT_TYPE + COMMA_SEP +
                    FeedReaderContract.Entry.COLUMN_NAME_NULLABLE + TEXT_TYPE +
                    // Any other options for the CREATE command, remember the COMMA_SEP
                    " )";*/

    private static final String SQL_CREATE_ENTRIES_FEEDS =
            "CREATE TABLE " + FeedReaderContract.Feed.TABLE_NAME + " (" +
                    FeedReaderContract.Feed._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.Feed.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    FeedReaderContract.Feed.COLUMN_NAME_FEED_URL + TEXT_TYPE + " UNIQUE " + COMMA_SEP +
                    FeedReaderContract.Feed.COLUMN_NAME_CREATOR + TEXT_TYPE + COMMA_SEP +
                    FeedReaderContract.Feed.COLUMN_NAME_SUBTITLE + TEXT_TYPE + COMMA_SEP +
                    FeedReaderContract.Feed.COLUMN_NAME_THUMBNAIL + TEXT_TYPE + COMMA_SEP +
                    FeedReaderContract.Feed.COLUMN_NAME_TIME_ADDED + " INTEGER " + COMMA_SEP +
                    FeedReaderContract.Feed.COLUMN_NAME_UUID + TEXT_TYPE + " UNIQUE " + COMMA_SEP +
                    FeedReaderContract.Feed.COLUMN_NAME_NULLABLE + TEXT_TYPE +
                    // Any other options for the CREATE command, remember the COMMA_SEP
                    " )";

/*
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.Entry.TABLE_NAME;
*/

    private static final String SQL_DELETE_ENTRIES_FEEDS =
            "DROP TABLE IF EXISTS " + FeedReaderContract.Feed.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "Feeds.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {

//        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES_FEEDS);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
//        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_ENTRIES_FEEDS);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
