package com.usn.ultimatesportnews.data.repository

import com.usn.ultimatesportnews.data.local.dao.NewsDao
import com.usn.ultimatesportnews.data.local.entities.*
import com.usn.ultimatesportnews.data.network.UsnWebServices
import com.usn.ultimatesportnews.domain.News
import com.usn.ultimatesportnews.domain.models.NewsStory
import com.usn.ultimatesportnews.domain.models.NewsVideo
import com.usn.ultimatesportnews.utils.mix
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface NewsRepository {
    suspend fun fetchNews(): Flow<List<News>>
    suspend fun fetchNewsStory(id: Int): Flow<NewsStory?>
    suspend fun fetchNewsVideo(id: Int): Flow<NewsVideo?>
}

class NewsDataRepository(
    private val newsApi: UsnWebServices,
    private val newsDao: NewsDao
): NewsRepository {

    var news: List<News> = emptyList()

    override suspend fun fetchNews(): Flow<List<News>> {
        return flow {
            if (news.isNotEmpty()) {
                emit(news)
            } else if (newsDao.countAllStories() > 0 && newsDao.countAllVideos() > 0) {
                val videos = newsDao.getAllVideos()
                val stories = newsDao.getAllStories()
                news = mergeNews(videos, stories)
                emit(news)
            } else {
                val data = newsApi.fetchNews()
                val videos = data.videos.toEntities()
                val stories = data.stories.toEntities()
                newsDao.insertVideos(videos)
                newsDao.insertStories(stories)
                news = mergeNews(videos, stories)
                emit(news)
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchNewsStory(id: Int): Flow<NewsStory?> {
        return flow {
            val story = newsDao.getStory(id)?.toNewsStory()
            emit(story)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchNewsVideo(id: Int): Flow<NewsVideo?> {
        return flow {
            val video = newsDao.getVideo(id)?.toNewsVideo()
            emit(video)
        }.flowOn(Dispatchers.IO)
    }

    private fun mergeNews(videos: List<NewsVideoEntity>, stories: List<NewsStoryEntity>): List<News> {
        return  mix(
            videos.toNewsVideos().sortedByDescending { it.date },
            stories.toNewsStories().sortedByDescending { it.date }
        )
    }
}