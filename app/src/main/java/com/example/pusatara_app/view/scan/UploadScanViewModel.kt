package com.example.pusatara_app.view.scan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pusatara_app.data.api.response.ScanResponseItem
import com.example.pusatara_app.data.api.retrofit.ApiConfig
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class UploadScanViewModel : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _uploadSuccess = MutableLiveData<Boolean>()
    val uploadSuccess: LiveData<Boolean> get() = _uploadSuccess

    fun uploadScanImage(token: String, imagePart: MultipartBody.Part) {
        viewModelScope.launch {
            try {
                _loading.value = true

                val responseList: List<ScanResponseItem> = ApiConfig.getApiService().uploadScan(token, imagePart)

                // Check if the responseList is not empty or handle accordingly
                if (responseList.isNotEmpty()) {
                    Log.e("UploadViewModel", "Uploaded successfully")
                    _uploadSuccess.value = true
                } else {
                    Log.e("UploadViewModel", "Upload failed: Empty response")
                    _uploadSuccess.value = false
                }
            } catch (e: Exception) {
                Log.e("UploadViewModel", "Exception: ${e.message}", e)
                _uploadSuccess.value = false
            } finally {
                _loading.value = false
            }
        }
    }
}