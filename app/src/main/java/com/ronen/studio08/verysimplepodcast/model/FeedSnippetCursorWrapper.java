package com.ronen.studio08.verysimplepodcast.model;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.ronen.studio08.verysimplepodcast.model.FeedSnippet;
import com.ronen.studio08.verysimplepodcast.model.database.FeedReaderContract;

import java.sql.Date;
import java.util.UUID;

/**
 * Created by Ronen on 24/8/16.
 */

public class FeedSnippetCursorWrapper extends CursorWrapper {
    public FeedSnippetCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public FeedSnippet getFeed() {
        String feedUrl = getString(getColumnIndexOrThrow(FeedReaderContract.Feed.COLUMN_NAME_FEED_URL));
        String title = getString(getColumnIndexOrThrow(FeedReaderContract.Feed.COLUMN_NAME_TITLE));
        String creator = getString(getColumnIndexOrThrow(FeedReaderContract.Feed.COLUMN_NAME_CREATOR));
        String subtitle = getString(getColumnIndexOrThrow(FeedReaderContract.Feed.COLUMN_NAME_SUBTITLE));
        String thumbnail = getString(getColumnIndexOrThrow(FeedReaderContract.Feed.COLUMN_NAME_THUMBNAIL));
        String date = getString(getColumnIndexOrThrow(FeedReaderContract.Feed.COLUMN_NAME_DATE));
        String feedUUID = getString(getColumnIndexOrThrow(FeedReaderContract.Feed.COLUMN_NAME_UUID));

        FeedSnippet feed = new FeedSnippet();
        feed.setFeedUrl(feedUrl);
        feed.setTitle(title);
        feed.setCreator(creator);
        feed.setSubtitle(subtitle);
        feed.setThumbnail(thumbnail);
        feed.setDate(Date.valueOf(date));
        feed.setUUID(UUID.fromString(feedUUID));
        return feed;
    }
}
