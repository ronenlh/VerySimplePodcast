package com.example.studio08.verysimplepodcast.model;

import org.simpleframework.xml.Element;

/**
 * Created by studio08 on 4/12/2016.
 */
public class Image
{
    @Element
    private String title;

    @Element
    private String link;

    @Element
    private String url;

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getLink ()
    {
        return link;
    }

    public void setLink (String link)
    {
        this.link = link;
    }

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
        return "ClassPojo [title = "+title+", link = "+link+", url = "+url+"]";
    }
}
