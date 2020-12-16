package com.example.yandexdiscapi;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.yandexdiscapi.Network.ApiClient.ACCESS_TOKEN;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    ImageView imgv;
//    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        
//        pdfView = findViewById(R.id.pdfView);
        imgv = findViewById(R.id.imgv);


        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<AllFiles> call = apiService.getAllFiles("OAuth " + ACCESS_TOKEN, 1000);
        call.enqueue(new Callback<AllFiles>() {
            @Override
            public void onResponse(Call<AllFiles> call, Response<AllFiles> response) {
                AllFiles data = response.body();
                List<File> files = data.getItems();
                List<File> menuFiles = new ArrayList<>();
                for (File file: files) {
                    String dir = file.getPath().split("/")[1];
                    if(dir.equals("Меню")){
                        menuFiles.add(file);
                    }
                }

//                String url = menuFiles.get(0).getPreview();
                 String url = "https://yadi.sk/i/DSRt9TeJSLP2hA";


                WebViewClient webViewClient = new WebViewClient(){
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                };
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(webViewClient);
                webView.loadUrl(url);
            }

            @Override
            public void onFailure(Call<AllFiles> call, Throwable t) {

            }
        });
    }

}