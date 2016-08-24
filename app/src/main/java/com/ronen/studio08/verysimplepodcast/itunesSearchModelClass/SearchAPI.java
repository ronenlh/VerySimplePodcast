package com.ronen.studio08.verysimplepodcast.itunesSearchModelClass;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by studio08 on 5/25/2016.
 * The Search API allows you to place search fields in your website to search for content within the iTunes Store, App Store, iBooks Store and Mac App Store.
 * You can search for a variety of content; including apps, iBooks, movies, podcasts, music, music videos, audiobooks, and TV shows.
 * You can also call an ID-based lookup request to create mappings between your content library and the digital catalog.
 * Developers may use promotional content in the API, including previews of songs, music videos, album art and App icons only to promote store content and not for entertainment purposes.
 * Use of sound samples and other assets from the API must be proximate to a store badge. Terms and conditions apply.
 */
public interface SearchAPI {
    // all params listed at https://affiliate.itunes.apple.com/resources/documentation/itunes-store-web-service-search-api/#searching
    @GET("search?media=podcast")
    Call<Search> search(@Query("country") String country,
                        @Query("limit") int limit,
                        @Query("term") String term,
                        @Query("explicit") String explicit,
                        @Query("lang") String lang);
}
