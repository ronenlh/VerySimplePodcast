package com.ronen.studio08.verysimplepodcast.database;

import android.provider.BaseColumns;

/**
 * Created by studio08 on 4/18/2016.
 */
public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
//        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_LINK = "link";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_MEDIA_URL = "url";
        public static final String COLUMN_NAME_NULLABLE = "null";

        /* author='null',
        category='null',
        comments='null',
        enclosure='null',
        guid='http://www.ivoox.com/11198463',
        pubDate='Mon, 18 Apr 2016 04:19:04 +0200',
        source='null'
        plus many more that are not in the model class and are ignored by simpleXML
        */
    }


}