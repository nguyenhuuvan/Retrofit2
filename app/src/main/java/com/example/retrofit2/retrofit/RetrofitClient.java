package com.example.retrofit2.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient(String baseUri) {
//        OkHttpClient builder = new OkHttpClient.Builder()
//                .readTimeout(1000, TimeUnit.MILLISECONDS)
//                .writeTimeout(1000, TimeUnit.MILLISECONDS)
//                .connectTimeout(1000, TimeUnit.MILLISECONDS)
//                .retryOnConnectionFailure(true)
//                .build();
//
//        Gson gsonBuilder=new GsonBuilder().setLenient().create();

        if (retrofit==null){
            retrofit= new Retrofit.Builder()
                    .baseUrl(baseUri)
                    //.client(builder)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;

    }

    ;
}
