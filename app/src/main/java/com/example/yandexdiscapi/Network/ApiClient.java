package com.example.yandexdiscapi.Network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static ApiClient mInstance;
    private static final String BASE_URL = "https://oauth.yandex.ru";
    private Retrofit mRetrofit;
    private static final String ID = "90e0e299b41a4a5fb6b31a9769583344";
    private static final String PASSWORD = "a334ea320d2c4c04bc40e5e460ef9e9b";
    private static final String CALLBACK_URI = "https://oauth.yandex.ru/verification_code";
    public static final String ACCESS_TOKEN = "AgAEA7qixWtmAAa-I0Etuxl6wEwBs0mLa2vI0vk";

    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if(retrofit == null){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }


}
