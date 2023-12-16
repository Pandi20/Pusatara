package com.example.pusatara_app.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pusatara_app.data.api.response.PatternsItem
import com.example.pusatara_app.data.api.retrofit.ApiConfig
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _patternItemList = MutableLiveData<List<PatternsItem>?>()
    val patternItemList: LiveData<List<PatternsItem>?> = _patternItemList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchByPattern(token: String, query: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().searchGlossary(token, query)
                _isLoading.value = false

                // Assuming searchGlossary returns List<PatternsItem> directly
                _patternItemList.postValue(response)
            } catch (e: Exception) {
                _isLoading.value = false
                // Handle API failure
                _error.postValue("API Failure: ${e.message}")
            }
        }
    }
}