package com.gonzaloandcompany.woldquiz.service;

import com.gonzaloandcompany.woldquiz.models.Pais;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PaisService {

    @GET("rest/v2/all")
    Call<List<Pais>> listPaises();
}
