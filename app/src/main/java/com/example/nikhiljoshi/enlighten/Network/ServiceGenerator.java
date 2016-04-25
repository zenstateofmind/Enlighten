package com.example.nikhiljoshi.enlighten.Network;

import com.example.nikhiljoshi.enlighten.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Retrofit takes in the Interface that contains all the API calls
 * and generates an implementation of the interface
 *
 * @author Nikhil Joshi
 */
public class ServiceGenerator {

    public static final String API_BASE_URL = "http://api.nytimes.com";
    private static OkHttpClient client = new OkHttpClient();

    public static <S> S createService(Class<S> serviceClass) {
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(serviceClass);
    }
}
