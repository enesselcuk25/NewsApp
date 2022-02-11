package com.enesselcuk.newsappagain.remote.service.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.enesselcuk.newsappagain.model.Article


@Database(entities = [Article::class], version = 1)

@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao

    companion object {

        @Volatile
        private var INSTANCE: ArticleDatabase? = null

        fun createDatabase(context: Context): ArticleDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArticleDatabase::class.java, "article_database.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}