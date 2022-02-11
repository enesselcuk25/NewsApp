package com.enesselcuk.newsappagain.remote.service.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.enesselcuk.newsappagain.model.Article


// Code with ❤️
//┌──────────────────────────┐
//│ Created by Enes SELÇUK │
//│ ──────────────────────── │
//│ enesselcuk@gmail.com      │            
//│ ──────────────────────── │
//│ 28.12.2021                │
//└──────────────────────────┘
@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles")
    fun getAllArticles():LiveData<List<Article>>

    @Insert(onConflict = REPLACE)
    suspend fun upsert(article: Article):Long

    @Delete
    suspend fun deleteArticle(article: Article)


}