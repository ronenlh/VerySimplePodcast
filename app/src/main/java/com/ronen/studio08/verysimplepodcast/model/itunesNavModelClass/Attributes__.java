
package com.ronen.studio08.verysimplepodcast.model.itunesNavModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attributes__ {

    @SerializedName("term")
    @Expose
    private String term;
    @SerializedName("label")
    @Expose
    private String label;

    /**
     * 
     * @return
     *     The term
     */
    public String getTerm() {
        return term;
    }

    /**
     * 
     * @param term
     *     The term
     */
    public void setTerm(String term) {
        this.term = term;
    }

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

}
