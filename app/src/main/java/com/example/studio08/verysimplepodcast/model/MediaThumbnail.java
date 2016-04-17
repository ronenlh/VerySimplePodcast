package com.example.studio08.verysimplepodcast.model;

/**
 * Created by studio08 on 4/12/2016.
 */
public class MediaThumbnail {
    private String url;

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [url = "+url+"]";
    }
}
