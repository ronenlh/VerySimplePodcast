package com.example.studio08.verysimplepodcast.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by studio08 on 4/18/2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    //  implement methods that create and maintain the database and tables
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedsContract.FeedEntry.TABLE_NAME + " (" +
                    FeedsContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedsContract.FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    FeedsContract.FeedEntry.COLUMN_NAME_FEED_URL + TEXT_TYPE + COMMA_SEP +
                    FeedsContract.FeedEntry.COLUMN_NAME_CREATOR + TEXT_TYPE + COMMA_SEP +
                    FeedsContract.FeedEntry.COLUMN_NAME_SUBTITLE + TEXT_TYPE + COMMA_SEP +
                    FeedsContract.FeedEntry.COLUMN_NAME_THUMBNAIL + TEXT_TYPE +
                    // Any other options for the CREATE command, remember the COMMA_SEP
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedsContract.FeedEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Feeds.db";

    public DbHelper(Context context) {
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
