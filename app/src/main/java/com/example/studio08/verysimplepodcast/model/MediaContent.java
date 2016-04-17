package com.example.studio08.verysimplepodcast.model;

/**
 * Created by studio08 on 4/12/2016.
 */
public class MediaContent {
    private String fileSize;

    private String type;

    private String url;

    public String getFileSize ()
    {
        return fileSize;
    }

    public void setFileSize (String fileSize)
    {
        this.fileSize = fileSize;
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
        return "ClassPojo [fileSize = "+fileSize+", type = "+type+", url = "+url+"]";
    }
}
