package com.gonzaloandcompany.woldquiz;

import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGeneratorPais {
    private static final String BASE_URL = "https://restcountries.eu/";

    private static Retrofit retrofit = null;

    public static <S> S createService(Class<S> serviceClass) {
        if (retrofit == null) {

            OkHttpClient.Builder httpClient =
                    new OkHttpClient.Builder();

            Retrofit.Builder retrofitBuilder =
                    new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(httpClient.build())
                            .addConverterFactory(GsonConverterFactory.create());
            retrofit = retrofitBuilder.build();
        }

        return retrofit.create(serviceClass);
    }

}
