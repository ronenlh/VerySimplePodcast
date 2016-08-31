package com.ronen.studio08.verysimplepodcast.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Ronen on 24/8/16.
 * This has the basic information to be queried into and from any other model object
 * there are 3 model object:
 *  1. The itunes navigation one from their Top Charts API: Entry.class / Feed.class
 *  2. the itunes search from the itunes search API: Result.class / Search.class
 *  3. the RSS from the canonical RSS feed of the podcast: RSS.class / Channel.class
 */

public class FeedSnippet implements Serializable {

    private UUID mUUID;
    private long mTimeAdded;
    private String feedUrl, website, title, creator, subtitle, thumbnail;

    public FeedSnippet() {
        mUUID = UUID.randomUUID();
        mTimeAdded = System.currentTimeMillis();
    }

    public UUID getUUID() {
        return mUUID;
    }

    public long getTimeAdded() {
        return mTimeAdded;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public void setTimeAdded(long timeAdded) {
        mTimeAdded = timeAdded;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    static public class Episode implements Serializable {
        private String title, description, itemUrl, mediaUrl, author, pubDate;

        public Episode() {
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getItemUrl() {
            return itemUrl;
        }

        public void setItemUrl(String itemUrl) {
            this.itemUrl = itemUrl;
        }

        public String getMediaUrl() {
            return mediaUrl;
        }

        public void setMediaUrl(String mediaUrl) {
            this.mediaUrl = mediaUrl;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getPubDate() {
            return pubDate;
        }

        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }
    }
}
