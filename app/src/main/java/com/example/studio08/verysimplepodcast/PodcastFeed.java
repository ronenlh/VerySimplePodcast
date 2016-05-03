package com.example.studio08.verysimplepodcast;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by studio08 on 4/10/2016.
 */
public class PodcastFeed {
    private String title;
    private String description = "sample description";
    private int thumbnailId = R.drawable.thumbnail99pi;

    public PodcastFeed(String title) {
        this.title = title;
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
