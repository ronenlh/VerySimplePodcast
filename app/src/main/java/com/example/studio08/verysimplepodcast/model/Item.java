package com.example.studio08.verysimplepodcast.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by studio08 on 4/12/2016.
 */
public class Item
{
    @Element(required=false)
    private Guid guid;

    @Element(required=false)
    private String pubDate;

    @Element
    private String title;

    @Element(required=false)
    private Enclosure enclosure;

    @Element(required=false)
    private String description;

    @Element
    private String link;

    public Guid getGuid ()
    {
        return guid;
    }

    public void setGuid (Guid guid)
    {
        this.guid = guid;
    }

    public String getPubDate ()
    {
        return pubDate;
    }

    public void setPubDate (String pubDate)
    {
        this.pubDate = pubDate;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public Enclosure getEnclosure ()
    {
        return enclosure;
    }

    public void setEnclosure (Enclosure enclosure)
    {
        this.enclosure = enclosure;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getLink ()
    {
        return link;
    }

    public void setLink (String link)
    {
        this.link = link;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [guid = "+guid+", pubDate = "+pubDate+", title = "+title+", enclosure = "+enclosure+", description = "+description+", link = "+link+"]";
    }
}