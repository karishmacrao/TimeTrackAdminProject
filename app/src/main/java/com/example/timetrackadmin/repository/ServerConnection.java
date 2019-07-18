package com.example.timetrackadmin.repository;

import com.example.timetrackadmin.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerConnection {

    public static ConnectionAPI getConnection() {

        OkHttpClient okHttpClient;
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        } else {
            okHttpClient = new OkHttpClient.Builder().build();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConnectionAPI.RegUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ConnectionAPI.class);
    }
}