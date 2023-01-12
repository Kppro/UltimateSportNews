package com.usn.ultimatesportnews.ui.screens.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usn.ultimatesportnews.data.repository.RepositoryManager
import com.usn.ultimatesportnews.domain.models.NewsVideo
import com.usn.ultimatesportnews.ui.screens.SingleEvent
import com.usn.ultimatesportnews.ui.screens.UiState
import kotlinx.coroutines.launch

class VideoViewModel: ViewModel() {

    var newsVideo: LiveData<SingleEvent<VideoUiState>> =
        MutableLiveData(SingleEvent(VideoUiState(isLoading = true)))

    fun fetchVideo(id: Int) {
        viewModelScope.launch {
            RepositoryManager.newsRepository.fetchNewsVideo(id).collect {
                (newsVideo as MutableLiveData).value =
                    SingleEvent(VideoUiState(data = it, isLoading = false))
            }
        }
    }
}

data class VideoUiState(
    override val data: NewsVideo? = null,
    override val isLoading: Boolean = false,
    override val error: Throwable? = null
) : UiState<NewsVideo>