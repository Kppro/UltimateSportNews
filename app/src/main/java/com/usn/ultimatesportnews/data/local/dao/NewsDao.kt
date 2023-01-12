package com.usn.ultimatesportnews.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.usn.ultimatesportnews.data.local.entities.NewsStoryEntity
import com.usn.ultimatesportnews.data.local.entities.NewsVideoEntity

@Dao
interface NewsDao {

    @Query("SELECT * FROM news_story")
    fun getAllStories(): List<NewsStoryEntity>

    @Query("SELECT * FROM news_video")
    fun getAllVideos(): List<NewsVideoEntity>

    @Query("SELECT * FROM news_video WHERE id = :id")
    fun getVideo(id: Int): NewsVideoEntity?

    @Query("SELECT * FROM news_story WHERE id = :id")
    fun getStory(id: Int): NewsStoryEntity?

    @Query("SELECT COUNT(*) FROM news_story")
    fun countAllStories(): Int

    @Query("SELECT COUNT(*) FROM news_video")
    fun countAllVideos(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideos(videos: List<NewsVideoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStories(stories: List<NewsStoryEntity>)
}