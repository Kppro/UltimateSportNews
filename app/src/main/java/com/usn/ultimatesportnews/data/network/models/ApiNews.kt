package com.usn.ultimatesportnews.data.network.models


data class ApiNews(
    val videos: List<ApiVideo>,
    val stories: List<ApiStory>
)