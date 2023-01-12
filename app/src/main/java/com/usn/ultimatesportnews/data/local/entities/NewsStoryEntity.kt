package com.usn.ultimatesportnews.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.usn.ultimatesportnews.data.network.models.ApiStory
import com.usn.ultimatesportnews.domain.models.NewsStory

@Entity(tableName = "news_story")
@TypeConverters(SportCategoryTypeConverter::class)
data class NewsStoryEntity(
    @PrimaryKey @ColumnInfo(name = "id") val storyId: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "teaser") val teaser: String?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "date") val date: Double?,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "sport_category") val sport: SportCategoryType?
)

fun List<ApiStory>.toEntities(): List<NewsStoryEntity> {
    return map {
        NewsStoryEntity(
            storyId = it.id ?: 0,
            title = it.title,
            teaser = it.teaser,
            image = it.image,
            date = it.date,
            author = it.author,
            sport = it.sport?.toEntity()
        )
    }
}

fun List<NewsStoryEntity>.toNewsStories(): List<NewsStory> {
    return map { it.toNewsStory() }
}

fun NewsStoryEntity.toNewsStory(): NewsStory {
    return NewsStory(
        storyId = this.storyId,
        title = this.title,
        teaser = this.teaser,
        image = this.image,
        timestamp = this.date,
        author = this.author,
        sport = this.sport?.toSportCategory()
    )
}