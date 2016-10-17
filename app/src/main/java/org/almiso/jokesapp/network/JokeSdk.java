package org.almiso.jokesapp.network;


import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class JokeSdk {

    public static final String BASE_URL = "http://api.icndb.com/";

    private static OkHttpClient sClient;

    @NonNull
    public static OkHttpClient getClient() {
        OkHttpClient client = sClient;
        if (client == null) {
            synchronized (JokeSdk.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = buildClient();
                }
            }
        }
        return client;
    }

    @NonNull
    private static OkHttpClient buildClient() {
        return new OkHttpClient();
//        return new OkHttpClient.Builder()
//                .addInterceptor(new ApiKeyInterceptor())
//                .build();
    }

    public static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(JokeSdk.BASE_URL)
                .client(JokeSdk.getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
}