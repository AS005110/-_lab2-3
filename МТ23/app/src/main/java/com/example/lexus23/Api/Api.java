package com.example.lexus23.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private static Api mInstance;
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private Retrofit mRetrofit;

    private Api(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Api getInstance(){
        if(mInstance == null){
            mInstance = new Api();
        }
        return mInstance;
    }

    public Service getJSON(){
        return mRetrofit.create(Service.class);
    }
}
