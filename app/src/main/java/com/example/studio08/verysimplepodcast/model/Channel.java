package com.example.studio08.verysimplepodcast.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by studio08 on 4/12/2016.
 */
public class Channel
{
    @Element(required=false)
    @Namespace(reference="http://www.itunes.com/dtds/podcast-1.0.dtd", prefix="itunes")
    private String author;

    @Element(data=true)
    private String title;

    @Element
    private String link;

    @Element(required=false)
    private String language;

    @Element(required=false)
    private String description;

    @Element(required=false)
    private String generator;

    @Element(required=false)
    private Image image;

    @Element(required=false)
    @Namespace(reference="http://www.itunes.com/dtds/podcast-1.0.dtd", prefix="itunes")
    private String explicit;

    @ElementList
    private List<Item> item;


    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public Item[] getItem ()
    {
        return item;
    }

    public void setItem (Item[] item)
    {
        this.item = item;
    }

    public String getLink ()
    {
        return link;
    }

    public void setLink (String link)
    {
        this.link = link;
    }

    public String getGenerator ()
    {
        return generator;
    }

    public void setGenerator (String generator)
    {
        this.generator = generator;
    }

    public Image getImage ()
    {
        return image;
    }

    public void setImage (Image image)
    {
        this.image = image;
    }

    public String getLanguage ()
    {
        return language;
    }

    public void setLanguage (String language)
    {
        this.language = language;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [title = "+title+", description = "+description+", item = "+item+", link = "+link+", generator = "+generator+", image = "+image+", language = "+language+"]";
    }
}
