package com.usn.ultimatesportnews.ui.screens

import androidx.compose.runtime.Stable


@Stable
interface UiState<T> {
    val data: T?
    val isLoading: Boolean
    val error: Throwable?
}