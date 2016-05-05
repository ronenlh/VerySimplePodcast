package com.example.studio08.verysimplepodcast;

/**
 * Created by studio08 on 4/10/2016.
 */
public class PodcastFeed {
    private String title;
    private String description = "sample description";
    private String feedUrl;
    private int thumbnailId = R.drawable.thumbnail99pi;

    public PodcastFeed(String title) {
        this.title = title;
    }

    public PodcastFeed(String title, String feedUrl) {
        this.title = title;
        this.feedUrl = feedUrl;
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

    public String getDescription() {
        return description;
    }

    public int getThumbnailId() {
        return thumbnailId;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
