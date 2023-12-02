package com.example.pusatara_app.data.di

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pusatara_app.data.api.response.DataItem
import com.example.pusatara_app.data.api.response.MediaResponse
import com.example.pusatara_app.data.api.retrofit.ApiService
import com.example.pusatara_app.view.media.MediaPagingSource
import kotlinx.coroutines.flow.Flow

class MediaRepository(private val apiService: ApiService, private val token: String) {
    fun getMediaPaging(): Flow<PagingData<DataItem>> {
        val pagingSourceFactory = { MediaPagingSource(apiService, token) }
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}
