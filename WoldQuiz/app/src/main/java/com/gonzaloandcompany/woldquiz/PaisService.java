package com.gonzaloandcompany.woldquiz;

import com.gonzaloandcompany.woldquiz.models.Pais;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PaisService {

    @GET("all")
    Call<List<Pais>> listPaises();

    @GET("currency/{currency}")
    Call<List<Pais>> listPaisesByMoneda(@Path("currency") String currency);

    @GET("lang/{et}")
    Call<List<Pais>> listPaisesByIdioma(@Path("et") String et);
}
