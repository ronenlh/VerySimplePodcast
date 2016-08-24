package com.ronen.studio08.verysimplepodcast.model.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by studio08 on 4/17/2016.
 */
public class ServiceGenerator {

    private static final String API_BASE_URL = "http://feeds.serialpodcast.org/";

    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static final Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}
