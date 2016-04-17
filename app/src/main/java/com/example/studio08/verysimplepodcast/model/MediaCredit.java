package com.example.studio08.verysimplepodcast.model;

/**
 * Created by studio08 on 4/12/2016.
 */
public class MediaCredit {
    private String content;

    private String role;

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public String getRole ()
    {
        return role;
    }

    public void setRole (String role)
    {
        this.role = role;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [content = "+content+", role = "+role+"]";
    }
}
