package com.usn.ultimatesportnews.domain.models

import com.github.marlonlom.utilities.timeago.TimeAgo
import com.github.marlonlom.utilities.timeago.TimeAgoMessages
import com.usn.ultimatesportnews.domain.News
import java.util.*


data class NewsStory(
    val storyId: Int,
    val title: String?,
    val teaser: String?,
    val image: String?,
    val timestamp: Double?,
    val author: String?,
    val sport: SportCategory?
): News {

    var date: Date? = null
    var timeAgo: String? = null

    init {
        initTimeAgo()
    }

    override fun getId(): Int = storyId

    override fun formatTitle(): String = title ?: ""

    override fun getSportCategoryName() = sport?.name?.uppercase()

    private fun initTimeAgo() {
        timestamp?.toLong()?.let {
            val timestampMillis = it * 1000
            val locale = Locale.forLanguageTag("en")
            val messages: TimeAgoMessages = TimeAgoMessages.Builder().withLocale(locale).build()
            timeAgo = TimeAgo.using(timestampMillis, messages)
        }
    }

}