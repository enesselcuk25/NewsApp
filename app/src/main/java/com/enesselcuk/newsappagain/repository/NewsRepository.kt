package com.enesselcuk.newsappagain.repository

import com.enesselcuk.newsappagain.model.Article
import com.enesselcuk.newsappagain.remote.service.RetrofitInstance
import com.enesselcuk.newsappagain.remote.service.db.ArticleDatabase

// Code with ❤️
//┌──────────────────────────┐
//│ Created by Enes SELÇUK │
//│ ──────────────────────── │
//│ enesselcuk@gmail.com      │            
//│ ──────────────────────── │
//│ 28.12.2021                │
//└──────────────────────────┘

class NewsRepository(private val db: ArticleDatabase) {

    suspend fun getBreakingNews(countyCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countyCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSaveNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)


}
