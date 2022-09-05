package com.example.pasteltakehome.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pasteltakehome.data.remote.models.Article

@Database(
    entities = [Article::class], version = 1, exportSchema = false
)
abstract class NewsArticleDatabase : RoomDatabase() {

    abstract fun newsArticleDao() : NewsArticleDao
}