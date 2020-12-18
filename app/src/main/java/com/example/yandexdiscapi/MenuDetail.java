package com.example.yandexdiscapi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.yandexdiscapi.Network.ApiClient;
import com.example.yandexdiscapi.Network.ApiService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.yandexdiscapi.Network.ApiClient.ACCESS_TOKEN;

public class MenuDetail extends AppCompatActivity {

//    ImageView imgvMenuDetail;
    SubsamplingScaleImageView imgvMenuDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);

//        imgvMenuDetail = findViewById(R.id.imgvMenuDetail);
        imgvMenuDetail = findViewById(R.id.imgvMenuDetail);

        String preview = getIntent().getExtras().getString("preview");
        preview = preview.replace("size=L", "size=XXXL");

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> callPreview = apiService.getPreview("OAuth " + ACCESS_TOKEN, preview);
        callPreview.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
//                        imgvMenuDetail.setImageBitmap(bmp);
                        imgvMenuDetail.setImage(ImageSource.bitmap(bmp));
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