package com.usn.ultimatesportnews.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.usn.ultimatesportnews.ui.screens.news.*
import com.usn.ultimatesportnews.ui.theme.UltimateSportNewsTheme
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.usn.ultimatesportnews.domain.models.NewsStory
import com.usn.ultimatesportnews.domain.models.NewsVideo
import com.usn.ultimatesportnews.ui.screens.story.StoryScreen
import com.usn.ultimatesportnews.ui.screens.story.StoryViewModel
import com.usn.ultimatesportnews.ui.screens.video.VideoViewModel
import com.usn.ultimatesportnews.ui.screens.video.startVideoActivity

class MainActivity : ComponentActivity() {

    private lateinit var newsVM: NewsViewModel
    private lateinit var storyVM: StoryViewModel
    private lateinit var videoVM: VideoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        newsVM = ViewModelProvider(this).get(NewsViewModel::class.java)
        storyVM = ViewModelProvider(this).get(StoryViewModel::class.java)
        videoVM = ViewModelProvider(this).get(VideoViewModel::class.java)

        videoVM.newsVideo.observe(this) {
            it.getContentIfNotConsumed().let { videoState ->
                videoState?.let {
                    if (it.error == null && !it.isLoading && it.data != null) {
                        it.data.url?.let { url ->
                            startVideoActivity(this, url)
                        }
                    }
                }
            }
        }

        setContent {
            UltimateSportNewsTheme {
                Surface(color = MaterialTheme.colors.background) {

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "news"
                    ) {
                        composable(route = "news") {
                            NewsScreen(
                                newsVM,
                                onClickItem = { newsItem ->
                                    when (newsItem) {
                                        is NewsStory -> {
                                            storyVM.fetchStory(newsItem.getId())
                                            navController.navigate("story/${newsItem.getId()}")
                                        }
                                        is NewsVideo -> {
                                            videoVM.fetchVideo(newsItem.getId())
                                        }
                                    }
                                },
                            )
                        }

                        composable(
                            route = "story/{storyId}",
                            arguments = listOf(
                                navArgument("storyId") { type = NavType.StringType }
                            )
                        ) {
                            StoryScreen(
                                navController,
                                storyVM
                            )
                        }
                    }

                }
            }
        }
    }
}
