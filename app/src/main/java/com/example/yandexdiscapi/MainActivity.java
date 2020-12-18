package com.example.yandexdiscapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yandexdiscapi.Adapter.IMenuListener;
import com.example.yandexdiscapi.Adapter.MenuAdapter;
import com.example.yandexdiscapi.Model.AllFiles;
import com.example.yandexdiscapi.Model.File;
import com.example.yandexdiscapi.Network.ApiClient;
import com.example.yandexdiscapi.Network.ApiService;
import com.github.barteksc.pdfviewer.PDFView;
import com.monstertechno.adblocker.AdBlockerWebView;
import com.monstertechno.adblocker.util.AdBlocker;


import org.adblockplus.libadblockplus.android.AdblockEngine;
import org.adblockplus.libadblockplus.android.webview.AdblockWebView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.yandexdiscapi.Network.ApiClient.ACCESS_TOKEN;

public class MainActivity extends AppCompatActivity implements IMenuListener {

//    WebView webView;
//    ImageView imgv;
//    PDFView pdfView;
    MenuAdapter menuAdapter;
    GridLayoutManager gridLayoutManager;
//    LinearLayoutManager linearLayoutManager;
    RecyclerView rvMenu;
    List<File> rendered_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        webView = findViewById(R.id.webView);
        
//        pdfView = findViewById(R.id.pdfView);
//        imgv = findViewById(R.id.imgv);
//        setTitle(null);
        gridLayoutManager = new GridLayoutManager(this, 2);
//        linearLayoutManager = new LinearLayoutManager(this);
        rvMenu = findViewById(R.id.rvMenu);
        menuAdapter = new MenuAdapter(this, this);
        rvMenu.setHasFixedSize(true);
        rvMenu.setLayoutManager(gridLayoutManager);
        rvMenu.setAdapter(menuAdapter);

        List<File> menuFiles = new ArrayList<>();

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<AllFiles> call = apiService.getAllFiles("OAuth " + ACCESS_TOKEN, 1000);
        call.enqueue(new Callback<AllFiles>() {
            @Override
            public void onResponse(Call<AllFiles> call, Response<AllFiles> response) {
                AllFiles data = response.body();
                List<File> files = data.getItems();

                for (File file: files) {
                    String dir = file.getPath().split("/")[1];
                    if(dir.equals("Меню")){
                        menuFiles.add(file);
                    }
                }

                // публикация неопубликованных файлов
                for (File file: menuFiles) {
                    if(file.getPublic_url() == null){
                        String path = file.getPath().split("disk:/")[1];
                        Call<ResponseBody> callPublish = apiService.publishFile("OAuth " + ACCESS_TOKEN, path);
                        callPublish.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Toast.makeText(MainActivity.this, "файл " + path + " опубликован", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                }
                rendered_data = new ArrayList<>();
                menuAdapter.setData(rendered_data);
                for (File file: menuFiles) {
                    String preview = file.getPreview();
                    preview = preview.replace("size=S", "size=L");
                    file.setPreview(preview);
                    Call<ResponseBody> callPreview = apiService.getPreview("OAuth " + ACCESS_TOKEN, preview);
                    callPreview.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                                    file.setBmp(bmp);
                                    rendered_data.add(file);
                                    menuAdapter.notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.d("error", t.getMessage());
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<AllFiles> call, Throwable t) {

            }
        });

    }

    @Override
    public void onMenuClickListener(int position) {
//        Intent i = new Intent(this, ItemPreview.class);
//        i.putExtra("public_url", rendered_data.get(position).getPublic_url());
        Intent i = new Intent(this, MenuDetail.class);
        i.putExtra("preview", rendered_data.get(position).getPreview());
        startActivity(i);
    }
}