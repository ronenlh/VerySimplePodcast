package com.ronen.studio08.verysimplepodcast.retrofitHeroku;

import java.util.List;

/**
 * Created by Ronen on 29/5/16.
 */
public class ResponseFeeds {
    List<FeedFromServer> feeds;

    public List<FeedFromServer> getFeeds() {
        return feeds;
    }

    private class FeedFromServer {

        private String title;
        private String description;
        private String feedUrl;
        private String imgHref;

        @Override
        public String toString() {
            return title;
        }
    }

    @Override
    public String toString() {
        return "ResponseFeeds{" +
                "feeds=" + feeds +
                '}';
    }
}


