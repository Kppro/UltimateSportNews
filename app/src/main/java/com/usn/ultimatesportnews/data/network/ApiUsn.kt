package com.usn.ultimatesportnews.data.network

import com.google.gson.GsonBuilder
import com.usn.ultimatesportnews.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUsn {

    private lateinit var instance: UsnWebServices

    fun init() {
        instance = create()
    }

    private fun create(): UsnWebServices {
        val okHttpClient = OkHttpClient.Builder()
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
            .create(UsnWebServices::class.java)
    }

    fun get(): UsnWebServices = instance
}