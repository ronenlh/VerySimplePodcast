package com.ronen.studio08.verysimplepodcast.model.retrofitCloud;

/**
 * Created by studio08 on 6/1/2016.
 */
public class SampleFeed {

    private String title;
    private String description;
    private String feedUrl;
    private String imgHref;

    @Override
    public String toString() {
        return title + ", " + description + ", " + feedUrl + ", " + imgHref + "\n";
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public String getImgHref() {
        return imgHref;
    }
}
