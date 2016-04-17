package com.example.studio08.verysimplepodcast.model;

/**
 * Created by studio08 on 4/12/2016.
 */
public class ItunesCategory {
    private String text;

    public String getText ()
    {
        return text;
    }

    public void setText (String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [text = "+text+"]";
    }
}
