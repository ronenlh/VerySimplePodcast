package com.example.studio08.verysimplepodcast.model;

/**
 * Created by studio08 on 4/12/2016.
 */
public class FeedburnerInfo {
    private String uri;

    public String getUri ()
    {
        return uri;
    }

    public void setUri (String uri)
    {
        this.uri = uri;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [uri = "+uri+"]";
    }
}
