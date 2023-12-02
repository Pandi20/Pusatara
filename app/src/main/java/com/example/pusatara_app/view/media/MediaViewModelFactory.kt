package com.example.pusatara_app.view.media

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pusatara_app.data.di.MediaRepository

class MediaViewModelFactory(private val mediaRepository: MediaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MediaViewModel::class.java)) {
            return MediaViewModel(mediaRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
