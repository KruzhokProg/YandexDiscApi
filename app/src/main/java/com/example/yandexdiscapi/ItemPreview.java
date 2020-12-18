package com.example.yandexdiscapi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ItemPreview extends AppCompatActivity {

    WebView menuWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_preview);


        menuWebView = findViewById(R.id.menuWebView);

        String publicURL = getIntent().getExtras().getString("public_url");

        WebViewClient webViewClient = new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

                if(url.contains("google")||url.contains("facebook")||url.contains("ya-recommendation-widget")){
                    InputStream textStream = new ByteArrayInputStream("".getBytes());
                    return getTextWebResource(textStream);
                }

                return super.shouldInterceptRequest(view, url);
            }
        };
        menuWebView.getSettings().setJavaScriptEnabled(true);
        menuWebView.getSettings().setBuiltInZoomControls(true);
        menuWebView.getSettings().setSupportZoom(true);
        menuWebView.setWebViewClient(webViewClient);
        menuWebView.loadUrl(publicURL);
    }

    private WebResourceResponse getTextWebResource(InputStream data) {
        return new WebResourceResponse("text/plain", "UTF-8", data);
    }
}