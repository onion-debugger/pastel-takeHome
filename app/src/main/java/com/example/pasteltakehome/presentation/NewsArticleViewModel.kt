package com.example.pasteltakehome.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteltakehome.data.repository.Repository
import com.example.pasteltakehome.presentation.uistate.NewsArticleUiState
import com.example.pasteltakehome.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsArticleViewModel @Inject constructor(
    private val repository: Repository) : ViewModel() {


    var newsUiState by mutableStateOf(NewsArticleUiState(progressLoading = true))
        private set

    init {
        getNewsArticles()
    }

    private fun getNewsArticles() {
        newsUiState = newsUiState.copy(progressLoading = true)

        viewModelScope.launch {
            repository.getAllNewsArticles().collect {
                when (it) {
                    is Resource.Error -> {
                        newsUiState = newsUiState.copy(
                            progressLoading = false,
                            newsArticles = it.data ?: arrayListOf(),
                            errorMessage = it.message ?: "Failed to Load Articles"
                        )
                    }

                    is Resource.Loading -> {
                        newsUiState = newsUiState.copy(
                            progressLoading = true
                        )
                    }

                    is Resource.Success -> {
                        newsUiState = newsUiState.copy(
                            progressLoading = false,
                            newsArticles = it.data ?: arrayListOf()
                        )
                    }
                }
            }
        }
    }

}