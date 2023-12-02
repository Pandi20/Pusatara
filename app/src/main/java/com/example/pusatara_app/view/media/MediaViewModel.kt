package com.example.pusatara_app.view.media

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pusatara_app.data.api.response.DataItem
import com.example.pusatara_app.data.di.MediaRepository

class MediaViewModel(mediaRepository: MediaRepository) : ViewModel() {
    val mediaList: LiveData<PagingData<DataItem>> =
        mediaRepository.getMediaPaging().cachedIn(viewModelScope).asLiveData()
}
