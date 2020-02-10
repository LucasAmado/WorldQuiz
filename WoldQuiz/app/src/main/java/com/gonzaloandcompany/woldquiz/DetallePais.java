package com.gonzaloandcompany.woldquiz;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.gonzaloandcompany.woldquiz.models.Pais;
import com.gonzaloandcompany.woldquiz.models.unsplash.Image;
import com.gonzaloandcompany.woldquiz.service.unsplash.ServiceGeneratorUnsplash;
import com.gonzaloandcompany.woldquiz.service.unsplash.UnsplashService;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DetallePais extends AppCompatActivity {

    UnsplashService service;
    TextView nombrePais;
    TextView capital;
    ImageView bandera;
    TextView idiomas;
    TextView nHabitantes;
    TextView moneda;
    CarouselView carrousel;
    LottieAnimationView location;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pais);

        nombrePais = findViewById(R.id.textViewNombrePais);
        capital = findViewById(R.id.textView8Capital);
        bandera = findViewById(R.id.imageViewBandera);
        idiomas = findViewById(R.id.textViewIdiona);
        nHabitantes = findViewById(R.id.textViewHabitantes);
        moneda = findViewById(R.id.textViewMoneda);
        carrousel = (CarouselView) findViewById(R.id.carrusel);
        Pais pais = (Pais) getIntent().getSerializableExtra("paisDetalle");
        location = findViewById(R.id.locationId);

        carrousel.setVisibility(View.GONE);
        nombrePais.setText(pais.getName());
        capital.setText(pais.getCapital());
        idiomas.setText(pais.getLanguages().get(0).getName());
        nHabitantes.setText(String.valueOf(pais.getPopulation()));
        moneda.setText(pais.getCurrencies().get(0).getName());
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.es/maps/place/" + pais.getName()));
                startActivity(i);
            }
        });

        Glide.with(this)
                .load("https://www.countryflags.io/" + pais.getAlpha2Code() + "/shiny/64.png")
                .into(bandera);


        service = ServiceGeneratorUnsplash.createService(UnsplashService.class);

        //TODO Pasar el nombre del país como parámetro
        new ImageUnsplashAsync().execute(pais.getName());
    }


    public class ImageUnsplashAsync extends AsyncTask<String, Void, Image> {

        Image imagenes;

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
            if (image.getResults().size() > 0)

            carrousel.setImageListener(new ImageListener() {
                @Override
                public void setImageForPosition(int position, ImageView imageView) {
                    //imageView.setImageResource(images.get(position).);
                    //imageView.setImageURI(Uri.parse(images.get(position).getResults().get(0).getUrls().getRegular()));
                    Glide.with(DetallePais.this)
                            .load(image.getResults().get(position).getUrls().getRegular()).into(imageView);
                }
            });
            carrousel.setPageCount(image.getResults().size());
            carrousel.setVisibility(View.VISIBLE);
        }


    }


}
