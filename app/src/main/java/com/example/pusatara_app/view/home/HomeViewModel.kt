package com.example.pusatara_app.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pusatara_app.data.api.response.BatikPatternsItem
import com.example.pusatara_app.data.api.response.SongketPatternsItem
import com.example.pusatara_app.data.api.retrofit.ApiConfig
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _batikPatternItemList = MutableLiveData<List<BatikPatternsItem>?>()
    val batikPatternItemList: LiveData<List<BatikPatternsItem>?> = _batikPatternItemList

    private val _songketPatternItemList = MutableLiveData<List<SongketPatternsItem>?>()
    val songketPatternItemList: LiveData<List<SongketPatternsItem>?> = _songketPatternItemList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllBatikPatterns(token: String, id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getBatikData(token, id)
                _isLoading.value = false

                _batikPatternItemList.postValue(response.patterns)
            } catch (e: Exception) {
                _isLoading.value = false
                _error.postValue("API Failure: ${e.message}")
            }
        }
    }

    fun getAllSongketPatterns(token: String, id:Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getSongketData(token, id)
                _isLoading.value = false

                _songketPatternItemList.postValue(response.patterns)
            } catch (e: Exception) {
                _isLoading.value = false
                _error.postValue("API Failure: ${e.message}")
            }
        }
    }
}