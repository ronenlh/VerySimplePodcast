package com.ronen.studio08.verysimplepodcast.itunes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronen on 5/6/16.
 */

public class Result {

    @SerializedName("wrapperType")
    @Expose
    private String wrapperType;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("collectionId")
    @Expose
    private Integer collectionId;
    @SerializedName("trackId")
    @Expose
    private Integer trackId;
    @SerializedName("artistName")
    @Expose
    private String artistName;
    @SerializedName("collectionName")
    @Expose
    private String collectionName;
    @SerializedName("trackName")
    @Expose
    private String trackName;
    @SerializedName("collectionCensoredName")
    @Expose
    private String collectionCensoredName;
    @SerializedName("trackCensoredName")
    @Expose
    private String trackCensoredName;
    @SerializedName("collectionViewUrl")
    @Expose
    private String collectionViewUrl;
    @SerializedName("feedUrl")
    @Expose
    private String feedUrl;
    @SerializedName("trackViewUrl")
    @Expose
    private String trackViewUrl;
    @SerializedName("artworkUrl30")
    @Expose
    private String artworkUrl30;
    @SerializedName("artworkUrl60")
    @Expose
    private String artworkUrl60;
    @SerializedName("artworkUrl100")
    @Expose
    private String artworkUrl100;
    @SerializedName("collectionPrice")
    @Expose
    private Integer collectionPrice;
    @SerializedName("trackPrice")
    @Expose
    private Integer trackPrice;
    @SerializedName("trackRentalPrice")
    @Expose
    private Integer trackRentalPrice;
    @SerializedName("collectionHdPrice")
    @Expose
    private Integer collectionHdPrice;
    @SerializedName("trackHdPrice")
    @Expose
    private Integer trackHdPrice;
    @SerializedName("trackHdRentalPrice")
    @Expose
    private Integer trackHdRentalPrice;
    @SerializedName("releaseDate")
    @Expose
    private String releaseDate;
    @SerializedName("collectionExplicitness")
    @Expose
    private String collectionExplicitness;
    @SerializedName("trackExplicitness")
    @Expose
    private String trackExplicitness;
    @SerializedName("trackCount")
    @Expose
    private Integer trackCount;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("primaryGenreName")
    @Expose
    private String primaryGenreName;
    @SerializedName("contentAdvisoryRating")
    @Expose
    private String contentAdvisoryRating;
    @SerializedName("artworkUrl600")
    @Expose
    private String artworkUrl600;
    @SerializedName("genreIds")
    @Expose
    private List<String> genreIds = new ArrayList<String>();
    @SerializedName("genres")
    @Expose
    private List<String> genres = new ArrayList<String>();

    /**
     *
     * @return
     *     The wrapperType
     */
    public String getWrapperType() {
        return wrapperType;
    }

    /**
     *
     * @param wrapperType
     *     The wrapperType
     */
    public void setWrapperType(String wrapperType) {
        this.wrapperType = wrapperType;
    }

    /**
     *
     * @return
     *     The kind
     */
    public String getKind() {
        return kind;
    }

    /**
     *
     * @param kind
     *     The kind
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     *
     * @return
     *     The collectionId
     */
    public Integer getCollectionId() {
        return collectionId;
    }

    /**
     *
     * @param collectionId
     *     The collectionId
     */
    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    /**
     *
     * @return
     *     The trackId
     */
    public Integer getTrackId() {
        return trackId;
    }

    /**
     *
     * @param trackId
     *     The trackId
     */
    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
    }

    /**
     *
     * @return
     *     The artistName
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     *
     * @param artistName
     *     The artistName
     */
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    /**
     *
     * @return
     *     The collectionName
     */
    public String getCollectionName() {
        return collectionName;
    }

    /**
     *
     * @param collectionName
     *     The collectionName
     */
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    /**
     *
     * @return
     *     The trackName
     */
    public String getTrackName() {
        return trackName;
    }

    /**
     *
     * @param trackName
     *     The trackName
     */
    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    /**
     *
     * @return
     *     The collectionCensoredName
     */
    public String getCollectionCensoredName() {
        return collectionCensoredName;
    }

    /**
     *
     * @param collectionCensoredName
     *     The collectionCensoredName
     */
    public void setCollectionCensoredName(String collectionCensoredName) {
        this.collectionCensoredName = collectionCensoredName;
    }

    /**
     *
     * @return
     *     The trackCensoredName
     */
    public String getTrackCensoredName() {
        return trackCensoredName;
    }

    /**
     *
     * @param trackCensoredName
     *     The trackCensoredName
     */
    public void setTrackCensoredName(String trackCensoredName) {
        this.trackCensoredName = trackCensoredName;
    }

    /**
     *
     * @return
     *     The collectionViewUrl
     */
    public String getCollectionViewUrl() {
        return collectionViewUrl;
    }

    /**
     *
     * @param collectionViewUrl
     *     The collectionViewUrl
     */
    public void setCollectionViewUrl(String collectionViewUrl) {
        this.collectionViewUrl = collectionViewUrl;
    }

    /**
     *
     * @return
     *     The feedUrl
     */
    public String getFeedUrl() {
        return feedUrl;
    }

    /**
     *
     * @param feedUrl
     *     The feedUrl
     */
    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    /**
     *
     * @return
     *     The trackViewUrl
     */
    public String getTrackViewUrl() {
        return trackViewUrl;
    }

    /**
     *
     * @param trackViewUrl
     *     The trackViewUrl
     */
    public void setTrackViewUrl(String trackViewUrl) {
        this.trackViewUrl = trackViewUrl;
    }

    /**
     *
     * @return
     *     The artworkUrl30
     */
    public String getArtworkUrl30() {
        return artworkUrl30;
    }

    /**
     *
     * @param artworkUrl30
     *     The artworkUrl30
     */
    public void setArtworkUrl30(String artworkUrl30) {
        this.artworkUrl30 = artworkUrl30;
    }

    /**
     *
     * @return
     *     The artworkUrl60
     */
    public String getArtworkUrl60() {
        return artworkUrl60;
    }

    /**
     *
     * @param artworkUrl60
     *     The artworkUrl60
     */
    public void setArtworkUrl60(String artworkUrl60) {
        this.artworkUrl60 = artworkUrl60;
    }

    /**
     *
     * @return
     *     The artworkUrl100
     */
    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    /**
     *
     * @param artworkUrl100
     *     The artworkUrl100
     */
    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    /**
     *
     * @return
     *     The collectionPrice
     */
    public Integer getCollectionPrice() {
        return collectionPrice;
    }

    /**
     *
     * @param collectionPrice
     *     The collectionPrice
     */
    public void setCollectionPrice(Integer collectionPrice) {
        this.collectionPrice = collectionPrice;
    }

    /**
     *
     * @return
     *     The trackPrice
     */
    public Integer getTrackPrice() {
        return trackPrice;
    }

    /**
     *
     * @param trackPrice
     *     The trackPrice
     */
    public void setTrackPrice(Integer trackPrice) {
        this.trackPrice = trackPrice;
    }

    /**
     *
     * @return
     *     The trackRentalPrice
     */
    public Integer getTrackRentalPrice() {
        return trackRentalPrice;
    }

    /**
     *
     * @param trackRentalPrice
     *     The trackRentalPrice
     */
    public void setTrackRentalPrice(Integer trackRentalPrice) {
        this.trackRentalPrice = trackRentalPrice;
    }

    /**
     *
     * @return
     *     The collectionHdPrice
     */
    public Integer getCollectionHdPrice() {
        return collectionHdPrice;
    }

    /**
     *
     * @param collectionHdPrice
     *     The collectionHdPrice
     */
    public void setCollectionHdPrice(Integer collectionHdPrice) {
        this.collectionHdPrice = collectionHdPrice;
    }

    /**
     *
     * @return
     *     The trackHdPrice
     */
    public Integer getTrackHdPrice() {
        return trackHdPrice;
    }

    /**
     *
     * @param trackHdPrice
     *     The trackHdPrice
     */
    public void setTrackHdPrice(Integer trackHdPrice) {
        this.trackHdPrice = trackHdPrice;
    }

    /**
     *
     * @return
     *     The trackHdRentalPrice
     */
    public Integer getTrackHdRentalPrice() {
        return trackHdRentalPrice;
    }

    /**
     *
     * @param trackHdRentalPrice
     *     The trackHdRentalPrice
     */
    public void setTrackHdRentalPrice(Integer trackHdRentalPrice) {
        this.trackHdRentalPrice = trackHdRentalPrice;
    }

    /**
     *
     * @return
     *     The releaseDate
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     *
     * @param releaseDate
     *     The releaseDate
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     *
     * @return
     *     The collectionExplicitness
     */
    public String getCollectionExplicitness() {
        return collectionExplicitness;
    }

    /**
     *
     * @param collectionExplicitness
     *     The collectionExplicitness
     */
    public void setCollectionExplicitness(String collectionExplicitness) {
        this.collectionExplicitness = collectionExplicitness;
    }

    /**
     *
     * @return
     *     The trackExplicitness
     */
    public String getTrackExplicitness() {
        return trackExplicitness;
    }

    /**
     *
     * @param trackExplicitness
     *     The trackExplicitness
     */
    public void setTrackExplicitness(String trackExplicitness) {
        this.trackExplicitness = trackExplicitness;
    }

    /**
     *
     * @return
     *     The trackCount
     */
    public Integer getTrackCount() {
        return trackCount;
    }

    /**
     *
     * @param trackCount
     *     The trackCount
     */
    public void setTrackCount(Integer trackCount) {
        this.trackCount = trackCount;
    }

    /**
     *
     * @return
     *     The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     *     The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     *     The currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     *
     * @param currency
     *     The currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     *
     * @return
     *     The primaryGenreName
     */
    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    /**
     *
     * @param primaryGenreName
     *     The primaryGenreName
     */
    public void setPrimaryGenreName(String primaryGenreName) {
        this.primaryGenreName = primaryGenreName;
    }

    /**
     *
     * @return
     *     The contentAdvisoryRating
     */
    public String getContentAdvisoryRating() {
        return contentAdvisoryRating;
    }

    /**
     *
     * @param contentAdvisoryRating
     *     The contentAdvisoryRating
     */
    public void setContentAdvisoryRating(String contentAdvisoryRating) {
        this.contentAdvisoryRating = contentAdvisoryRating;
    }

    /**
     *
     * @return
     *     The artworkUrl600
     */
    public String getArtworkUrl600() {
        return artworkUrl600;
    }

    /**
     *
     * @param artworkUrl600
     *     The artworkUrl600
     */
    public void setArtworkUrl600(String artworkUrl600) {
        this.artworkUrl600 = artworkUrl600;
    }

    /**
     *
     * @return
     *     The genreIds
     */
    public List<String> getGenreIds() {
        return genreIds;
    }

    /**
     *
     * @param genreIds
     *     The genreIds
     */
    public void setGenreIds(List<String> genreIds) {
        this.genreIds = genreIds;
    }

    /**
     *
     * @return
     *     The genres
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     *
     * @param genres
     *     The genres
     */
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

}
