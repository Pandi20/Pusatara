package com.example.pusatara_app.view.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pusatara_app.data.api.response.ScanResponseItem
import okhttp3.MultipartBody

class ScanOutputViewModel : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _scanOutput = MutableLiveData<List<ScanResponseItem>>()
    val scanOutput: LiveData<List<ScanResponseItem>> get() = _scanOutput

    fun loadScanResults(token: String, imagePart: MultipartBody.Part) {
        // In this case, the loadScanResults function is not needed as
        // scan results are already obtained during the image upload.
        // The ScanOutputActivity can directly observe the scanOutput LiveData.
    }
}