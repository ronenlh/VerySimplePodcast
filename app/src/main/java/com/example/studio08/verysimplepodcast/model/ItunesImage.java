package com.example.studio08.verysimplepodcast.model;

/**
 * Created by studio08 on 4/12/2016.
 */
public class ItunesImage {
    private String href;

    public String getHref ()
    {
        return href;
    }

    public void setHref (String href)
    {
        this.href = href;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [href = "+href+"]";
    }
}
