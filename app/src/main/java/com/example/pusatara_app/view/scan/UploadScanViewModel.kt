package com.example.pusatara_app.view.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

                // Make the API call to upload the image
                val response = ApiConfig.getApiService().uploadScan(token, imagePart)
                if (response.isSuccessful) {
                    // Image upload successful
                    _uploadSuccess.value = true
                } else {
                    // Image upload failed
                    _uploadSuccess.value = false
                    // You can handle the error response here if needed
                }
            } catch (e: Exception) {
                _uploadSuccess.value = false
            } finally {
                _loading.value = false
            }
        }
    }
}