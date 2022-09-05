package com.example.pasteltakehome.data.repository

import com.example.pasteltakehome.data.remote.models.Article
import com.example.pasteltakehome.utils.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getAllNewsArticles() : Flow<Resource<List<Article>>>
}
