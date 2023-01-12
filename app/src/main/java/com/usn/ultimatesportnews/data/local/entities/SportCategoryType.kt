package com.usn.ultimatesportnews.data.local.entities

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.usn.ultimatesportnews.data.network.models.ApiSport
import com.usn.ultimatesportnews.domain.models.SportCategory

data class SportCategoryType(
    val id: Int,
    val name: String?
)

class SportCategoryTypeConverter {
    @TypeConverter
    fun fromJson(json: String): SportCategoryType {
        val type = object : TypeToken<SportCategoryType>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(sportCategoryType: SportCategoryType): String {
        val type = object : TypeToken<SportCategoryType>() {}.type
        return Gson().toJson(sportCategoryType, type)
    }
}

fun ApiSport.toEntity(): SportCategoryType {
    return SportCategoryType(
        id = id ?: 0,
        name = name
    )
}

fun SportCategoryType.toSportCategory(): SportCategory {
    return SportCategory(
        sportId = id,
        name = name
    )
}