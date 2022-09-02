package com.example.pasteltakehome.presentation.uistate

import com.example.pasteltakehome.data.remote.models.Article

data class NewsArticleUiState(
    val newsArticles: List<Article> = arrayListOf(),
    val progressLoading: Boolean = false,
    val errorMessage: String = ""
)
