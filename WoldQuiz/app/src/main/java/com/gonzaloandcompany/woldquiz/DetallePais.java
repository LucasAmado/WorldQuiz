package com.gonzaloandcompany.woldquiz;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.gonzaloandcompany.woldquiz.models.unsplash.Image;
import com.gonzaloandcompany.woldquiz.service.unsplash.ServiceGeneratorUnsplash;
import com.gonzaloandcompany.woldquiz.service.unsplash.UnsplashService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class DetallePais extends AppCompatActivity {

    UnsplashService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pais);

        service = ServiceGeneratorUnsplash.createService(UnsplashService.class);

        //TODO Pasar el nombre del país como parámetro
        new ImageUnsplashAsync().execute();
    }



    public class ImageUnsplashAsync extends AsyncTask<String, Void, Image> {

        @Override
        protected Image doInBackground(String... strings) {
            Image img = new Image();

            Call<Image> imageCall = service.getImages(strings[0]);
            Response<Image> imageResponse = null;

            try {
                imageResponse = imageCall.execute();

                if (imageResponse.isSuccessful()) {
                    img = imageResponse.body();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return img;
        }

        @Override
        protected void onPostExecute(Image image) {

        }

    }


}
