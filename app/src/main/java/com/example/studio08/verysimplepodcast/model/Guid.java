package com.example.studio08.verysimplepodcast.model;

import org.simpleframework.xml.Element;

/**
 * Created by studio08 on 4/12/2016.
 */
public class Guid
{
    @Element(required=false)
    private String content;

    @Element(required=false)
    private String isPermaLink;

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public String getIsPermaLink ()
    {
        return isPermaLink;
    }

    public void setIsPermaLink (String isPermaLink)
    {
        this.isPermaLink = isPermaLink;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [content = "+content+", isPermaLink = "+isPermaLink+"]";
    }
}
