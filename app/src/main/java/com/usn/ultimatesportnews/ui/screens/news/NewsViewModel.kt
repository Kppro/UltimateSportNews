package com.usn.ultimatesportnews.ui.screens.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usn.ultimatesportnews.data.repository.RepositoryManager
import com.usn.ultimatesportnews.domain.News
import com.usn.ultimatesportnews.ui.screens.UiState
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    val newsFlow: LiveData<NewsUiState> = MutableLiveData(NewsUiState(isLoading = true))

    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            RepositoryManager.newsRepository
                .fetchNews()
                .collect {
                    (newsFlow as MutableLiveData)
                        .postValue(NewsUiState(data = it, isLoading = false))
                }
        }
    }
}

data class NewsUiState(
    override val data: List<News> = emptyList(),
    override val isLoading: Boolean = false,
    override val error: Throwable? = null
) : UiState<List<News>>