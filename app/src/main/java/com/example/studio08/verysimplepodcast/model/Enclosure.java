package com.example.studio08.verysimplepodcast.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by studio08 on 4/12/2016.
 */
public class Enclosure
{
    @Element
    private String length;

    @Element
    private String type;

    @Element
    private String url;

    public String getLength ()
    {
        return length;
    }

    public void setLength (String length)
    {
        this.length = length;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
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
        return "ClassPojo [length = "+length+", type = "+type+", url = "+url+"]";
    }
}
