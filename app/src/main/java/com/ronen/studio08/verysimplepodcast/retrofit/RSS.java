package com.ronen.studio08.verysimplepodcast.retrofit;

/**
 * Created by Ronen on 17/4/16.
 */
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class RSS {

    @Attribute
    private
    String version;

    @Element
    private
    Channel channel;


    public Channel getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        return "RSS{" +
                "\n\t version='" + version + '\'' +
                ", \n\t channel=" + channel +
                '}';
    }
}
