package com.example.abdelrahmansaleh.mvpnewsapp.data.rest;

import com.example.abdelrahmansaleh.mvpnewsapp.data.model.news.NewsBasic;

import io.reactivex.Observable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("top-headlines")
    Observable<NewsBasic> NEWS_BASIC_GET_CALL(@Query("country") String country,
                                              @Query("apiKey") String apiKey);
    @GET("everything")
    Observable<NewsBasic> NEWS_SEARCH_CALL(@Query("q") String keyWord,
                                     @Query("sortBy") String sortBy,
                                     @Query("apiKey") String apiKey);
}
