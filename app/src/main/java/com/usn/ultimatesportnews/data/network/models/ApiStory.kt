package com.usn.ultimatesportnews.data.network.models


data class ApiStory(
    val id: Int?,
    val title: String?,
    val teaser: String?,
    val image: String?,
    val date: Double?,
    val author: String?,
    val sport: ApiSport?
)