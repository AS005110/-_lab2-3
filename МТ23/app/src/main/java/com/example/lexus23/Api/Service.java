package com.example.lexus23.Api;

import com.example.lexus23.Model.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("photos/")
    Call<List<Photo>> getPhotos();
}
