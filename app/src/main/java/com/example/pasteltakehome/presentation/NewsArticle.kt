package com.example.pasteltakehome.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.pasteltakehome.R
import com.example.pasteltakehome.data.remote.models.Article
import com.example.pasteltakehome.ui.theme.Cream
import com.example.pasteltakehome.ui.theme.Dark
import com.example.pasteltakehome.ui.theme.Grey
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Composable
@Destination(start = true)
fun NewsArticle(
    navigator: DestinationsNavigator,
    viewModel: NewsArticleViewModel = hiltViewModel()
){

    val state = viewModel.newsUiState

    Scaffold(
        topBar = { AppBar(title = "Newsfeed") },
    )
    {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = stringResource(id = R.string.news),
                style = TextStyle(
                    color = Cream,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Grey)
                    .padding(vertical = 4.dp)
            )

            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.newsArticles) { articles ->
                        NewsItem(
                            newsArticle = articles,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    ArticleDetailsDestination(articles.url)
                                }
                        )

                        Divider(
                            modifier = Modifier.padding(
                                horizontal = 16.dp
                            )
                        )
                    }
                }

                if (!state.errorMessage.isBlank()) {
                    Text(
                        text = state.errorMessage,
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .align(Alignment.Center)
                    )
                }

                if (state.progressLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}


@Composable
fun NewsItem(
    newsArticle: Article,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        AsyncImage(
            model = newsArticle.urlToImage,
            contentDescription = newsArticle.description,
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = 300f
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = newsArticle.title ?: "Title",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = newsArticle.author ?: "Author",
                    style = TextStyle(
                        color = Cream,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black
                    )
                )
            }
        }

    }
}


@Composable
fun AppBar(
    title: String
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(Dark)

    ) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}