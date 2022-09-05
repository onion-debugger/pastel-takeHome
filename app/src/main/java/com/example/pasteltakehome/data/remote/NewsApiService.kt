package com.example.pasteltakehome.data.remote

import com.example.pasteltakehome.data.remote.models.NewsResponse
import com.example.pasteltakehome.utils.Constant.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers

interface NewsApiService {

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines?country=us")
    suspend fun getAllNewsArticle(): NewsResponse
}