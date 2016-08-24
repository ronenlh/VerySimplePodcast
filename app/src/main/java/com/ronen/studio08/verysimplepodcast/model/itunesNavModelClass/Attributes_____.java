
package com.ronen.studio08.verysimplepodcast.model.itunesNavModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attributes_____ {

    @SerializedName("im:id")
    @Expose
    private String imId;
    @SerializedName("term")
    @Expose
    private String term;
    @SerializedName("scheme")
    @Expose
    private String scheme;
    @SerializedName("label")
    @Expose
    private String label;

    /**
     * 
     * @return
     *     The imId
     */
    public String getImId() {
        return imId;
    }

    /**
     * 
     * @param imId
     *     The im:id
     */
    public void setImId(String imId) {
        this.imId = imId;
    }

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
     *     The scheme
     */
    public String getScheme() {
        return scheme;
    }

    /**
     * 
     * @param scheme
     *     The scheme
     */
    public void setScheme(String scheme) {
        this.scheme = scheme;
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
