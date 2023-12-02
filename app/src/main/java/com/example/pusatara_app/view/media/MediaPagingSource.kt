package com.example.pusatara_app.view.media

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pusatara_app.data.api.response.DataItem
import com.example.pusatara_app.data.api.response.MediaResponse
import com.example.pusatara_app.data.api.retrofit.ApiService

class MediaPagingSource(private val apiService: ApiService, private val token: String) : PagingSource<Int, DataItem>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val response = apiService.getAllMedia("Bearer $token", position, params.loadSize)
            val data = response.data

            LoadResult.Page(
                data = data,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (data.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}