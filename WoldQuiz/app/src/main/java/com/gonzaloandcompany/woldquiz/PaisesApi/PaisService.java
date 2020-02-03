package com.gonzaloandcompany.woldquiz.PaisesApi;

import com.gonzaloandcompany.woldquiz.models.Pais;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PaisService {

    @GET("/search/photos")
    Call<List<Pais>> listRepos();
}
