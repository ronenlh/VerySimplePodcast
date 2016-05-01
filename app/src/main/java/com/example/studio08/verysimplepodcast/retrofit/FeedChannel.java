package com.example.studio08.verysimplepodcast.retrofit;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.util.List;

/**
 * Created by Ronen on 17/4/16.
 */
@NamespaceList({
        @Namespace(reference = "http://www.w3.org/2005/Atom", prefix = "atom")
})
@Root(strict = false)
public class FeedChannel {
    // Tricky part in Simple XML because the link is named twice
    @ElementList(entry = "link", inline = true, required = false)
    public List<Link> links;

    @ElementList(name = "item", required = true, inline = true)
    public List<Item> itemList;


    @Element
    String title;
    @Element
    String language;

    @Element(name = "ttl", required = false)
    int ttl;

    @Element(name = "pubDate", required = false)
    String pubDate;

    @Override
    public String toString() {
        return "Channel{" +
                "links=" + links +
                ", itemList=" + itemList +
                ", title='" + title + '\'' +
                ", language='" + language + '\'' +
                ", ttl=" + ttl +
                ", pubDate='" + pubDate + '\'' +
                '}';
    }

    public static class Link {
        @Attribute(required = false)
        public String href;

        @Attribute(required = false)
        public String rel;

        @Attribute(name = "type", required = false)
        public String contentType;

        @Text(required = false)
        public String link;
    }

    @Root(name = "item", strict = false)
    public static class Item {

        @Element(name = "title", required = true)
        public String title;
        @Element(name = "link", required = true)
        public String link;
        @Element(name = "description", required = true)
        public String description;
        @Element(name = "author", required = false)
        public String author;
        @Element(name = "category", required = false)
        public String category;
        @Element(name = "comments", required = false)
        public String comments;
        @Element(name = "enclosure", required = true)
        public Enclosure enclosure;//	Describes a media object that is attached to the item. More.	<enclosure url="http://live.curry.com/mp3/celebritySCms.mp3" length="1069871" type="audio/mpeg"/>
        @Element(name = "guid", required = false)
        public String guid;//A string that uniquely identifies the item. More.	<guid isPermaLink="true">http://inessential.com/2002/09/01.php#a2</guid>
        @Element(name = "pubDate", required = false)
        public String pubDate;//	Indicates when the item was published. More.	Sun, 19 May 2002 15:21:36 GMT
        @Element(name = "source", required = false)
        public String source;//	The RSS channel that the item came from. More.

        @Override
        public String toString() {
            return "\n\t Item{" +
                    "title='" + title + '\'' +
                    ",  link='" + link + '\'' +
                    ", description='" + description + '\'' +
                    ", author='" + author + '\'' +
                    ", category='" + category + '\'' +
                    ", comments='" + comments + '\'' +
                    ", enclosure='" + enclosure + '\'' +
                    ", guid='" + guid + '\'' +
                    ", pubDate='" + pubDate + '\'' +
                    ", source='" + source + '\'' +
                    '}';
        }
    }
    public static class Enclosure {
        @Attribute(required = true)
        public String url;

        @Attribute(required = false)
        public String type;

        @Attribute(required = false)
        public long length;

        @Override
        public String toString() {
            return "Enclosure{" +
                    "url='" + url + '\'' +
                    ", type='" + type + '\'' +
                    ", length='" + length + '\'' +
                    "}";
        }
    }

}
