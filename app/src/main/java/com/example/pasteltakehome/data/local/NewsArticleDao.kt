package com.example.pasteltakehome.data.local

import androidx.room.*
import com.example.pasteltakehome.data.remote.models.Article
import kotlinx.coroutines.flow.Flow


@Dao
interface NewsArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsArticles(article: Article)

    @Transaction
    @Query("SELECT * FROM news_article_table")
    fun getNewsArticles() : Flow<List<Article>>

    @Query("DELETE FROM news_article_table")
    suspend fun deleteAllArticles()
}