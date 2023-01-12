package com.usn.ultimatesportnews.domain.models

import com.usn.ultimatesportnews.domain.News
import java.text.NumberFormat
import java.util.*

data class NewsVideo(
    val videoId: Int,
    val title: String?,
    val thumb: String?,
    val url: String?,
    val date: Double?,
    val sport: SportCategory?,
    val views: Int?
): News {

    val viewsFormated: String by lazy {
        NumberFormat.getInstance(Locale.US).format(views)
    }

    override fun getId(): Int = videoId

    override fun formatTitle(): String = title ?: ""

    override fun getSportCategoryName() = sport?.name?.uppercase()
}
