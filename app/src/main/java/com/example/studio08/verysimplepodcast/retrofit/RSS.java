package com.example.studio08.verysimplepodcast.retrofit;

/**
 * Created by Ronen on 17/4/16.
 */
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class RSS {

    @Attribute
    String version;

    @Element
    FeedChannel channel;


    public FeedChannel getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        return "RSS{" +
                "version='" + version + '\'' +
                ", channel=" + channel +
                '}';
    }
}