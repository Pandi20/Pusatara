package com.example.pusatara_app.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pusatara_app.data.api.response.SearchResponseItem
import com.example.pusatara_app.data.api.retrofit.ApiConfig
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _searchItemList = MutableLiveData<List<SearchResponseItem>?>()
    val searchItemList: LiveData<List<SearchResponseItem>?> = _searchItemList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchByPattern(token: String, q: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().searchGlossary(token, q)
                _isLoading.value = false
                _searchItemList.postValue(response)
            } catch (e: Exception) {
                _isLoading.value = false
                _error.postValue("API Failure: ${e.message}")
            }
        }
    }
}