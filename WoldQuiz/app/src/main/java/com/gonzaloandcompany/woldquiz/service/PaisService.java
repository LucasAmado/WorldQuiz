package com.gonzaloandcompany.woldquiz.service;

import com.gonzaloandcompany.woldquiz.models.Pais;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PaisService {

    @GET("all")
    Call<List<Pais>> listPaises();
}
