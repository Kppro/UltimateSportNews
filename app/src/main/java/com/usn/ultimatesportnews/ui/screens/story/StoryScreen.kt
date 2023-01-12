package com.usn.ultimatesportnews.ui.screens.story

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.usn.ultimatesportnews.R
import com.usn.ultimatesportnews.domain.models.NewsStory
import com.usn.ultimatesportnews.ui.screens.ProgressIndicator
import com.usn.ultimatesportnews.utils.generateText

@Composable
fun StoryScreen(navController: NavController, vm: StoryViewModel) {
    Scaffold(
        content = { innerPadding ->
            StoryScreenContent(
                navController = navController,
                vm = vm,
                Modifier.padding(innerPadding)
            )
        },
        backgroundColor = MaterialTheme.colors.secondaryVariant
    )
}

@Composable
fun StoryScreenContent(navController: NavController, vm: StoryViewModel, padding: Modifier) {
    val newsStoryState = vm.story.observeAsState().value

    if (newsStoryState?.isLoading != false) {
        ProgressIndicator()
    } else if (newsStoryState.error != null) {
        Text(text = newsStoryState.error.message ?: stringResource(id = R.string.generic_error_msg))
    } else {
        newsStoryState.data?.let {
            val scrollState = rememberScrollState()
            Column(Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(scrollState)
                .background(Color.White)) {
                StoryHeader(navController, it)
                StoryBody(it)
            }
        }
    }
}

@Composable
fun StoryBody(newsStory: NewsStory) {
    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        generateText(4).let {
            for (paragraph in it) {
                Text(
                    text = paragraph,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    fontWeight = MaterialTheme.typography.body1.fontWeight,
                    textAlign = TextAlign.Justify,
                )
            }
        }
    }
}

@Composable
fun StoryHeader(navController: NavController, newsStory: NewsStory) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(310.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                AsyncImage(
                    model = newsStory.image,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                )

                Icon(
                    modifier = Modifier
                        .clickable {
                            navController.navigateUp()
                        }
                        .padding(10.dp)
                        .align(Alignment.TopStart),
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "",
                    tint = Color.White
                )

                Icon(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.TopEnd),
                    painter = painterResource(id = R.drawable.share),
                    contentDescription = "",
                    tint = Color.White
                )
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
                    Text(text = newsStory.formatTitle(),
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = MaterialTheme.typography.h6.fontWeight,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Box(modifier = Modifier
                    .height(20.dp)
                ) {
                    newsStory.author?.let { author ->
                        val timeAgo = if (newsStory.timeAgo != null) " - ${newsStory.timeAgo!!}" else ""
                        val authorText = stringResource(id = R.string.story_author, author)
                        Text(text = "$authorText$timeAgo",
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 12.sp,
                            color = Color.Gray,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }

        newsStory.getSportCategoryName()?.let { sportName ->
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