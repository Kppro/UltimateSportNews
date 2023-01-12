package com.usn.ultimatesportnews.ui.screens.news

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.*
import coil.compose.AsyncImage
import com.usn.ultimatesportnews.R
import com.usn.ultimatesportnews.domain.News
import com.usn.ultimatesportnews.domain.models.NewsStory
import com.usn.ultimatesportnews.domain.models.NewsVideo
import com.usn.ultimatesportnews.ui.screens.ProgressIndicator


@Composable
fun NewsScreen(vm: NewsViewModel, onClickItem: (news: News) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(stringResource(id = R.string.title_page_news),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center)
                },
                backgroundColor = MaterialTheme.colors.primaryVariant
            )
         },
        content = { innerPadding ->
            NewsScreenContent(
                vm = vm,
                Modifier.padding(innerPadding),
                onClickItem = onClickItem
            )
        },
        backgroundColor = MaterialTheme.colors.secondaryVariant
    )
}

@Composable
fun NewsScreenContent(vm: NewsViewModel, padding: Modifier, onClickItem: (news: News) -> Unit) {
    val newsUiState by vm.newsFlow.observeAsState()

    if (newsUiState?.isLoading != false) {
        ProgressIndicator()
    } else if (newsUiState?.error != null) {
        Snackbar { Text(text = newsUiState?.error?.message ?: "Unknown error") }
    } else {
        NewsList(newsList = newsUiState?.data ?: emptyList(), onClickItem = onClickItem)
    }
}

@Composable
fun NewsList(newsList: List<News>, onClickItem: (news: News) -> Unit) {
    LazyColumn(modifier = Modifier.padding(10.dp)) {
        items(newsList, key = { item: News ->  item.getId()}) { news ->
            NewsItem(news, onClickItem)
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
fun NewsItem(news: News, onClickItem: (news: News) -> Unit) {
    Box(modifier = Modifier
        .clip(RoundedCornerShape(8.dp))
        .background(Color.White)
        .fillMaxWidth()
        .height(310.dp)
        .clickable {
            onClickItem(news)
        }
    ) {

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                when (news) {
                    is NewsStory -> AsyncImage(
                        model = news.image,
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                    )
                    is NewsVideo -> {
                        AsyncImage(
                            model = news.thumb,
                            modifier = Modifier.fillMaxSize(),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.play),
                            contentDescription = "",
                            modifier = Modifier.align(Alignment.Center),
                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Column(modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 0.dp)
            ) {
                Box(modifier = Modifier
                    .height(60.dp)
                ) {
                    Text(text = news.formatTitle(),
                        modifier = Modifier
                            .fillMaxWidth(),
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = MaterialTheme.typography.h6.fontWeight,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Box(modifier = Modifier
                    .height(20.dp)
                ) {
                    when (news) {
                        is NewsStory -> news.author?.let { author ->
                            val timeAgo = if (news.timeAgo != null) " - ${news.timeAgo!!}" else ""
                            val authorText = stringResource(id = R.string.story_author, author)
                            Text(text = "$authorText$timeAgo",
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 12.sp,
                                color = Color.Gray,
                            )
                        }
                        is NewsVideo -> {
                            Text(text = stringResource(id = R.string.video_views, news.viewsFormated.replace(",", ".")),
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }

        news.getSportCategoryName()?.let { sportName ->
            Box(modifier = Modifier
                .absoluteOffset(x = 10.dp, y = 185.dp)
                .height(30.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(color = MaterialTheme.colors.primaryVariant)
            ) {
                Text(text = sportName,
                    modifier = Modifier.padding(6.dp),
                    fontSize = MaterialTheme.typography.subtitle2.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}


