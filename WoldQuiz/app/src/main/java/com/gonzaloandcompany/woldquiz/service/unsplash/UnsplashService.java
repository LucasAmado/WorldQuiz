package com.gonzaloandcompany.woldquiz.service.unsplash;

import com.gonzaloandcompany.woldquiz.models.unsplash.Image;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UnsplashService {

    @GET("search/photos")
    Call<Image> getImages (@Query("query") String nombrePais);
}
