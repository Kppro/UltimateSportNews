package com.usn.ultimatesportnews.domain

interface News {
    fun getId(): Int
    fun formatTitle(): String
    fun getSportCategoryName(): String?
}