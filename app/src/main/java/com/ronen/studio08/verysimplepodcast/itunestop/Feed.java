
package com.ronen.studio08.verysimplepodcast.itunestop;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feed {

    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("entry")
    @Expose
    private List<Entry> entry = new ArrayList<>();
    @SerializedName("updated")
    @Expose
    private Updated updated;
    @SerializedName("rights")
    @Expose
    private Rights_ rights;
    @SerializedName("title")
    @Expose
    private Title_ title;
    @SerializedName("icon")
    @Expose
    private Icon icon;
    @SerializedName("link")
    @Expose
    private List<Link_> link = new ArrayList<>();
    @SerializedName("id")
    @Expose
    private Id_ id;

    /**
     * 
     * @return
     *     The author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     *     The author
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * 
     * @return
     *     The entry
     */
    public List<Entry> getEntry() {
        return entry;
    }

    /**
     * 
     * @param entry
     *     The entry
     */
    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }

    /**
     * 
     * @return
     *     The updated
     */
    public Updated getUpdated() {
        return updated;
    }

    /**
     * 
     * @param updated
     *     The updated
     */
    public void setUpdated(Updated updated) {
        this.updated = updated;
    }

    /**
     * 
     * @return
     *     The rights
     */
    public Rights_ getRights() {
        return rights;
    }

    /**
     * 
     * @param rights
     *     The rights
     */
    public void setRights(Rights_ rights) {
        this.rights = rights;
    }

    /**
     * 
     * @return
     *     The title
     */
    public Title_ getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(Title_ title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The icon
     */
    public Icon getIcon() {
        return icon;
    }

    /**
     * 
     * @param icon
     *     The icon
     */
    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    /**
     * 
     * @return
     *     The link
     */
    public List<Link_> getLink() {
        return link;
    }

    /**
     * 
     * @param link
     *     The link
     */
    public void setLink(List<Link_> link) {
        this.link = link;
    }

    /**
     * 
     * @return
     *     The id
     */
    public Id_ getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Id_ id) {
        this.id = id;
    }

}
