
package com.ronen.studio08.verysimplepodcast.itunesNavModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImContentType {

    @SerializedName("attributes")
    @Expose
    private Attributes__ attributes;

    /**
     * 
     * @return
     *     The attributes
     */
    public Attributes__ getAttributes() {
        return attributes;
    }

    /**
     * 
     * @param attributes
     *     The attributes
     */
    public void setAttributes(Attributes__ attributes) {
        this.attributes = attributes;
    }

}
