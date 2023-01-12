package com.usn.ultimatesportnews.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.usn.ultimatesportnews.data.network.models.ApiVideo
import com.usn.ultimatesportnews.domain.models.NewsVideo

@Entity(tableName = "news_video")
@TypeConverters(SportCategoryTypeConverter::class)
data class NewsVideoEntity(
    @PrimaryKey @ColumnInfo(name = "id") val videoId: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "thumb") val thumb: String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "date") val date: Double?,
    @ColumnInfo(name = "sport_category") val sport: SportCategoryType?,
    @ColumnInfo(name = "views") val views: Int?
)


fun List<ApiVideo>.toEntities(): List<NewsVideoEntity> {
    return map {
        NewsVideoEntity(
            videoId = it.id ?: 0,
            title = it.title,
            thumb = it.thumb,
            url = it.url,
            date = it.date,
            sport = it.sport?.toEntity(),
            views = it.views ?: 0
        )
    }
}

fun List<NewsVideoEntity>.toNewsVideos(): List<NewsVideo> {
    return map { it.toNewsVideo() }
}

fun NewsVideoEntity.toNewsVideo(): NewsVideo {
    return NewsVideo(
        videoId = this.videoId,
        title = this.title,
        thumb = this.thumb,
        url = this.url,
        date = this.date,
        sport = this.sport?.toSportCategory(),
        views = this.views
    )
}