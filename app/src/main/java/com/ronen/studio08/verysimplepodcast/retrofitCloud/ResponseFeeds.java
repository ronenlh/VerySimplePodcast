package com.ronen.studio08.verysimplepodcast.retrofitCloud;

import java.util.List;

/**
 * Created by Ronen on 29/5/16.
 */
class ResponseFeeds {
    private List<FeedFromServer> feeds;

    public List<FeedFromServer> getFeeds() {
        return feeds;
    }

    public class FeedFromServer {

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

    @Override
    public String toString() {
        return "ResponseFeeds{" +
                "feeds=" + feeds +
                '}';
    }
}


