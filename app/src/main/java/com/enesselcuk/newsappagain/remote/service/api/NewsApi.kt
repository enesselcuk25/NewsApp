package com.enesselcuk.newsappagain.remote.service.api

import com.enesselcuk.newsappagain.model.NewsResponse
import com.enesselcuk.newsappagain.util.Constans.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


// Code with ❤️
//┌──────────────────────────┐
//│ Created by Enes SELÇUK │
//│ ──────────────────────── │
//│ enesselcuk@gmail.com      │            
//│ ──────────────────────── │
//│ 28.12.2021                │
//└──────────────────────────┘

// https://newsapi.org/v2/top-headlines?country=tr&apiKey=0ddbbcf218ac4183add60139876aa243

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode:String = "tr",
        @Query("page")
        pageNumber:Int = 1,
        @Query("apiKey")
        apiKey:String =API_KEY
    ):Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery:String,
        @Query("page")
        pageNumber:Int = 1,
        @Query("apiKey")
        apiKey:String =API_KEY
    ):Response<NewsResponse>
}