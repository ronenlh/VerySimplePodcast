package com.example.studio08.verysimplepodcast.database;

import android.provider.BaseColumns;

/**
 * Created by studio08 on 4/18/2016.
 */
public final class FeedsContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedsContract() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "feeds";
//        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_FEED_URL = "url";
        public static final String COLUMN_NAME_CREATOR = "creator";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
        public static final String COLUMN_NAME_THUMBNAIL = "thumbnail";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }


}