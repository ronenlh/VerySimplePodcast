package com.example.studio08.verysimplepodcast.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by studio08 on 4/12/2016.
 */
public class Rss
{
    @Element
    private Channel channel;

    @Element(required=false)
    private String version;

    public Channel getChannel ()
    {
        return channel;
    }

    public void setChannel (Channel channel)
    {
        this.channel = channel;
    }

    public String getVersion ()
    {
        return version;
    }

    public void setVersion (String version)
    {
        this.version = version;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [channel = "+channel+", version = "+version+"]";
    }
}
