package com.aj.jav.service;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

//    @GET("v1/videos/menu/{menu_id}")
//    Call<String> getCall(@Path("menu_id") String menu_id, @Query("token") String token, @Query("video_type") String video_type, @Query("page") int page , @Query("order") String order);

    @GET("v1/videos/menu/{menu_id}")
    Observable<Response<String>> getVideoList(@Path("menu_id") String menu_id, @Query("token") String token, @Query("video_type") String video_type, @Query("page") int page, @Query("order") String order, @Query("top") String top);
}
