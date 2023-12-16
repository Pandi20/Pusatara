package com.example.pusatara_app.view.scan

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pusatara_app.R
import com.example.pusatara_app.data.di.UserPreferences
import com.example.pusatara_app.databinding.ActivityUploadScanBinding
import com.example.pusatara_app.getImageUri
import com.example.pusatara_app.reduceFileImage
import com.example.pusatara_app.uriToFile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

@Suppress("DEPRECATION")
class UploadScanActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUploadScanBinding
    private lateinit var userPreferences : UserPreferences
    private var currentImageUri: Uri? = null
    private var token: String? = null
    private lateinit var adapter: ScanOutputAdapter
    private lateinit var viewModel: UploadScanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.upload_scan_bar_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userPreferences = UserPreferences.getInstance(applicationContext)
        viewModel = ViewModelProvider(this)[UploadScanViewModel::class.java]

        lifecycleScope.launch {
            token = userPreferences.getToken().first()
        }

        binding.btnGalleryScan.setOnClickListener {
            startGallery()
        }

        binding.btnCameraScan.setOnClickListener {
            startCamera()
        }

        binding.btnUploadScan.setOnClickListener {
            uploadScanImage()
        }

        viewModel.loading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.uploadSuccess.observe(this) { uploadSuccess ->
            if (uploadSuccess) {
                hideButtons()
                showToast("Image uploaded successfully")
            } else {
                showButtons()
                showToast("Image upload failed")
            }
        }

        adapter = ScanOutputAdapter()
        binding.rvScanUpload.layoutManager = LinearLayoutManager(this)
        binding.rvScanUpload.adapter = adapter

        viewModel.scanResponse.observe(this) { scanResponse ->
            Log.d("scanResults", "Scan Results: $scanResponse")
            if (scanResponse.isNotEmpty()) {
                adapter.submitList(scanResponse)
            }
        }

        binding.tvTitleResult.visibility = View.GONE
    }

    private fun hideButtons() {
        binding.btnGalleryScan.visibility = View.GONE
        binding.btnCameraScan.visibility = View.GONE
        binding.btnUploadScan.visibility = View.GONE
        binding.tvTitleResult.visibility = View.VISIBLE
    }

    private fun showButtons() {
        binding.btnGalleryScan.visibility = View.VISIBLE
        binding.btnCameraScan.visibility = View.VISIBLE
        binding.btnUploadScan.visibility = View.VISIBLE
        binding.tvTitleResult.visibility = View.GONE
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivUploadScan.setImageURI(it)
        }
    }

    private fun uploadScanImage() {
        lifecycleScope.launchWhenCreated {

            currentImageUri?.let { uri ->
                val imageFile = uriToFile(uri, this@UploadScanActivity).reduceFileImage()
                Log.d("Image File", "showImage: ${imageFile.path}")

                showLoading(true)

                val imageRequestBody = imageFile.asRequestBody("image/jpeg".toMediaType())
                val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, imageRequestBody)

                viewModel.uploadScanImage("Bearer $token", imagePart)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}