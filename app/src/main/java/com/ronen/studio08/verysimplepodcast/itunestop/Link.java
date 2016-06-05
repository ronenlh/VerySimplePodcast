
package com.ronen.studio08.verysimplepodcast.itunestop;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Link {

    @SerializedName("attributes")
    @Expose
    private Attributes___ attributes;

    /**
     * 
     * @return
     *     The attributes
     */
    public Attributes___ getAttributes() {
        return attributes;
    }

    /**
     * 
     * @param attributes
     *     The attributes
     */
    public void setAttributes(Attributes___ attributes) {
        this.attributes = attributes;
    }

}
