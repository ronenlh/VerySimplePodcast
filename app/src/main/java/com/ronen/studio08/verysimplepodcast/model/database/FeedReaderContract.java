package com.ronen.studio08.verysimplepodcast.model.database;

import android.provider.BaseColumns;

/**
 * Created by studio08 on 4/18/2016.
 */
public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {}

    /* Inner class that defines the table contents */
/*    public static abstract class Entry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
//        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_LINK = "link";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_MEDIA_URL = "url";
        public static final String COLUMN_NAME_NULLABLE = "nullable";

    }*/

    public static abstract class Feed implements BaseColumns {
        public static final String TABLE_NAME = "feeds";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_FEED_URL = "url";
        public static final String COLUMN_NAME_CREATOR = "creator";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
        public static final String COLUMN_NAME_THUMBNAIL = "thumbnail";
        public static final String COLUMN_NAME_TIME_ADDED = "time_added";
        public static final String COLUMN_NAME_UUID = "uuid";
        public static final String COLUMN_NAME_NULLABLE = "nullable";
    }


}