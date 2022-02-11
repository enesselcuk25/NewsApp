package com.enesselcuk.newsappagain.remote.service

import com.enesselcuk.newsappagain.remote.service.api.NewsApi
import com.enesselcuk.newsappagain.util.Constans.Companion.BASE_URl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Enes SELÇUK │
//│ ──────────────────────── │
//│ enesselcuk@gmail.com      │            
//│ ──────────────────────── │
//│ 28.12.2021                │
//└──────────────────────────┘

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        val api: NewsApi by lazy {
            retrofit.create(NewsApi::class.java)
        }
    }
} 
