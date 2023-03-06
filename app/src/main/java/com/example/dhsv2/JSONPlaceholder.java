package com.example.dhsv2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface JSONPlaceholder {
    //passing API headers
    @Headers({"Content-Type: application/json",
    "x-api-key: 63722be4c890f30a8fd1f370",
    "cache-control': no-cache"})

    @GET("group-1")
    Call<List<User>> getUser();


}
