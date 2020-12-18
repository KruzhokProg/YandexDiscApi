package com.example.yandexdiscapi.Network;


import com.example.yandexdiscapi.Model.AllFiles;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {
    @GET("/v1/disk/resources/files")
    Call<AllFiles> getAllFiles(
            @Header("Authorization") String accessToken,
            @Query("limit") Integer limit
    );

    @PUT("/v1/disk/resources/publish")
    Call<ResponseBody> publishFile(
            @Header("Authorization") String accessToken,
            @Query("path") String path
    );

    @GET
    Call<ResponseBody> getPreview(
            @Header("Authorization") String accessToken,
            @Url String preview_url
    );
}
