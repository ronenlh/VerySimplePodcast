
package com.ronen.studio08.verysimplepodcast.model.itunesNavModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImPrice {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("attributes")
    @Expose
    private Attributes_ attributes;

    /**
     * 
     * @return
     *     The label
     */
    public String getLabel() {
        return label;
    }

    /**
     * 
     * @param label
     *     The label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 
     * @return
     *     The attributes
     */
    public Attributes_ getAttributes() {
        return attributes;
    }

    /**
     * 
     * @param attributes
     *     The attributes
     */
    public void setAttributes(Attributes_ attributes) {
        this.attributes = attributes;
    }

}
