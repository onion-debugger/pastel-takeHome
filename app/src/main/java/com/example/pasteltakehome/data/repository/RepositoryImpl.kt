package com.example.pasteltakehome.data.repository

import androidx.room.withTransaction
import com.example.pasteltakehome.data.local.NewsArticleDatabase
import com.example.pasteltakehome.data.remote.NewsApiService
import com.example.pasteltakehome.data.remote.models.Article
import com.example.pasteltakehome.di.Dispatcher
import com.example.pasteltakehome.di.ResourceDispatcher
import com.example.pasteltakehome.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: NewsApiService,
    private val database: NewsArticleDatabase,
    @Dispatcher(ResourceDispatcher.IO) private val dispatcher: CoroutineDispatcher
) : Repository  {

    override suspend fun getAllNewsArticles(): Flow<Resource<List<Article>>> = flow {

        val newsArticleDao = database.newsArticleDao()

        emit(Resource.Loading())

        val localData = newsArticleDao.getNewsArticles().first()


        if (!localData.isEmpty()) {
            emit(Resource.Success(localData))
        }

        val remoteData = try {
            apiService.getAllNewsArticle()
        } catch (exception: Exception) {
            newsArticleDao.getNewsArticles().collect { article ->
                emit(Resource.Error(exception.message ?: "Database failed to load articles", article))
            }
            null
        }

        remoteData?.let {
            database.withTransaction {
                newsArticleDao.deleteAllArticles()
                for (article in it.articles) {
                    newsArticleDao.insertNewsArticles(article)
                }
            }

            newsArticleDao.getNewsArticles().collect{
                emit(Resource.Success(it))
            }
        }


    }.flowOn(dispatcher)
}