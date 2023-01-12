package com.usn.ultimatesportnews.data.network.models


data class ApiVideo(
    val id: Int?,
    val title: String?,
    val thumb: String?,
    val url: String?,
    val date: Double?,
    val sport: ApiSport?,
    val views: Int?
)