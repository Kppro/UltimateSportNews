package com.usn.ultimatesportnews.ui.screens

open class SingleEvent<out T>(
    private val content: T
) {

    var hasBeenConsumed = false
        private set

    fun getContentIfNotConsumed(): T? {
        return if (hasBeenConsumed) {
            null
        } else {
            hasBeenConsumed = true
            content
        }
    }

    fun getContent(): T = content
}