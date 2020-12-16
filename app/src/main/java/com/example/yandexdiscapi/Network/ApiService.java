package com.example.yandexdiscapi.Network;


import com.example.yandexdiscapi.Model.AllFiles;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiService {
    @GET("https://cloud-api.yandex.net/v1/disk/resources/files")
    Call<AllFiles> getAllFiles(
            @Header("Authorization") String accessToken,
            @Query("limit") Integer limit
    );
}
