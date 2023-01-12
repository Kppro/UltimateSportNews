package com.usn.ultimatesportnews.ui.screens.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usn.ultimatesportnews.data.repository.RepositoryManager
import com.usn.ultimatesportnews.domain.models.NewsStory
import com.usn.ultimatesportnews.ui.screens.UiState
import kotlinx.coroutines.launch
import timber.log.Timber

class StoryViewModel: ViewModel() {

    var story: LiveData<StoryUiState> = MutableLiveData(StoryUiState(isLoading = true))

    fun fetchStory(id: Int) {
        viewModelScope.launch {
            RepositoryManager.newsRepository.fetchNewsStory(id).collect {
                (story as MutableLiveData).value = (StoryUiState(data = it, isLoading = false))
            }
        }
    }
}

data class StoryUiState(
    override val data: NewsStory? = null,
    override val isLoading: Boolean = false,
    override val error: Throwable? = null
) : UiState<NewsStory>