package com.usn.ultimatesportnews.data.network

import com.usn.ultimatesportnews.data.network.models.ApiNews
import retrofit2.http.GET

interface  UsnWebServices {

    @GET("edfefba")
    suspend fun fetchNews(): ApiNews
}