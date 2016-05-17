package com.example.studio08.verysimplepodcast.retrofit;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public List<Item> getItemList() {
        return itemList;
    }

    @Element
    String title;

    @Element
    String language;

    @Element(name = "author", required = false)
    @Namespace(reference = "http://www.itunes.com/dtds/podcast-1.0.dtd", prefix = "itunes")
    String author;

    @Element(name = "subtitle", required = false)
    @Namespace(reference = "http://www.itunes.com/dtds/podcast-1.0.dtd", prefix = "itunes")
    String subtitle;

    public String getAuthor() {
        return author;
    }

    public String getSubtitle() {
        return subtitle;
    }

    @Element(name = "image", required = true)
    @Namespace(reference = "http://www.itunes.com/dtds/podcast-1.0.dtd", prefix = "itunes")
    public ItunesImage itunesImage;

    public String getImage() {
        return itunesImage.getHref();
    }

    @Element(name = "ttl", required = false)
    int ttl;

    @Element(name = "pubDate", required = false)
    String pubDate;

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "links=" + links +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", language='" + language + '\'' +
                ", ItunesImage='" + itunesImage + '\'' +
                ", ttl=" + ttl +
                ", pubDate='" + pubDate + '\'' +
                ", itemList=" + itemList +
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
        public String category;  // should check this: https://stackoverflow.com/questions/31999265/parsing-xml-feed-die-with-element-is-already-used
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


        public String getTitle() {
            return title;
        }

        public String getLink() {
            return link;
        }

        public String getDescription() {
            // remove html tags from description
            String sanitizedDescription = description.replaceAll("<[^>]*>", "");
            return sanitizedDescription;
        }

        public String getAuthor() {
            return author;
        }

        public String getCategory() {
            return category;
        }

        public String getComments() {
            return comments;
        }

        public Enclosure getEnclosure() {
            return enclosure;
        }

        public String getGuid() {
            return guid;
        }

        public String getPubDate() {
            return pubDate;
        }

        public Date getPubDateParsed() throws ParseException {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z "); // Thu, 31 Mar 2016 09:30:00 +0000
            Date parsedDate = dateFormat.parse(getPubDate());
            return parsedDate;
        }

        public String getSource() {
            return source;
        }

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

        public String getUrl() {
            return url;
        }

        @Override
        public String toString() {
            return "Enclosure{" +
                    "url='" + url + '\'' +
                    ", type='" + type + '\'' +
                    ", length='" + length + '\'' +
                    "}";
        }
    }

    @Root(name = "image", strict = false)
    @Namespace(reference = "http://www.itunes.com/dtds/podcast-1.0.dtd", prefix = "itunes")
    public static class ItunesImage {
        @Attribute(required = false)
        public String href;

        public String getHref() {
            return href;
        }

        @Override
        public String toString() {
            return href;
        }
    }
}
