package com.example.studio08.verysimplepodcast.model;

/**
 * Created by studio08 on 4/12/2016.
 */
public class MediaCategory {
    private String content;

    private String scheme;

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public String getScheme ()
    {
        return scheme;
    }

    public void setScheme (String scheme)
    {
        this.scheme = scheme;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [content = "+content+", scheme = "+scheme+"]";
    }
}
