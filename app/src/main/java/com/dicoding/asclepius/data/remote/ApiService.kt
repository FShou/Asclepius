package com.dicoding.asclepius.data.remote

import com.dicoding.asclepius.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("v2/top-headlines")
    suspend fun getNews(

        @Query("q")
        q :String = "cancer",

        @Query("category")
        category: String = "health",

        @Query("language")
        language: String = "en",

        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY


    ): Response
}