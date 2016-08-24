package com.ronen.studio08.verysimplepodcast.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Ronen on 24/8/16.
 */

public class FeedSnippet {

    private UUID mUUID;
    private Date mDate;
    private String feedUrl, title, creator, subtitle, thumbnail;

    public FeedSnippet() {
        mUUID = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getUUID() {
        return mUUID;
    }

    public Date getDate() {
        return mDate;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
