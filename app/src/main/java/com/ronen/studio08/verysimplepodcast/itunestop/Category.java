
package com.ronen.studio08.verysimplepodcast.itunestop;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("attributes")
    @Expose
    private Attributes_____ attributes;

    /**
     * 
     * @return
     *     The attributes
     */
    public Attributes_____ getAttributes() {
        return attributes;
    }

    /**
     * 
     * @param attributes
     *     The attributes
     */
    public void setAttributes(Attributes_____ attributes) {
        this.attributes = attributes;
    }

}
