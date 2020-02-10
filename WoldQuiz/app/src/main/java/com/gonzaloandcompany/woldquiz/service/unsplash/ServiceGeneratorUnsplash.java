package com.gonzaloandcompany.woldquiz.service.unsplash;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGeneratorUnsplash {

    private static final String BASE_URL = "https://api.unsplash.com/";

    private static Retrofit retrofit = null;

    public static <S> S createService(Class<S> serviceClass) {
        if (retrofit == null) {
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                    .addInterceptor((new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            String IdClient = "0a2ce36bc64ba23aa86458788504df5fc70868bbbb35d77e0d10d6098a84cee1";
                            Request original = chain.request();
                            HttpUrl originalHttpUrl = original.url();

                            HttpUrl url = originalHttpUrl.newBuilder()
                                    .addQueryParameter("client_id", IdClient)
                                    .build();

                            Request.Builder requestBuilder = original.newBuilder()
                                    .url(url);

                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                    }
                    ));

            Retrofit.Builder retrofitBuilder =
                    new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(httpClientBuilder.build())
                            .addConverterFactory(GsonConverterFactory.create());
            retrofit = retrofitBuilder.build();
        }

        return retrofit.create(serviceClass);
    }


}