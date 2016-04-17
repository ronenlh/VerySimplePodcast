package com.example.studio08.verysimplepodcast.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by studio08 on 4/12/2016.
 * xmlns:itunes="http://www.itunes.com/dtds/podcast-1.0.dtd"
 * xmlns:atom="http://www.w3.org/2005/Atom"
 * xmlns:media="http://search.yahoo.com/mrss/"
 * xmlns:feedburner="http://rssnamespace.org/feedburner/ext/1.0" version="2.0">
 * @Namespace(reference="http://www.w3.org/2005/Atom", prefix="atom")
 * @Namespace(reference="http://rssnamespace.org/feedburner/ext/1.0", prefix="feedburner")
 * @Namespace(reference="http://search.yahoo.com/mrss/", prefix="media")
 * @Namespace(reference="http://www.itunes.com/dtds/podcast-1.0.dtd", prefix="itunes")
 */
@Root
public class Feed
{
    @Element
    private Rss rss;

    public Rss getRss ()
    {
        return rss;
    }

    public void setRss (Rss rss)
    {
        this.rss = rss;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [rss = "+rss+"]";
    }
}
