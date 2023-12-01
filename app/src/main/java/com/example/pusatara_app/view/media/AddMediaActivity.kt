package com.example.pusatara_app.view.media

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.example.pusatara_app.R
import com.example.pusatara_app.data.api.response.MediaResponse
import com.example.pusatara_app.data.api.retrofit.ApiConfig
import com.example.pusatara_app.data.di.UserPreferences
import com.example.pusatara_app.databinding.ActivityAddMediaBinding
import com.example.pusatara_app.getImageUri
import com.example.pusatara_app.reduceFileImage
import com.example.pusatara_app.uriToFile
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

@Suppress("DEPRECATION")
class AddMediaActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddMediaBinding
    private var currentImageUri: Uri? = null
    private lateinit var userPreferences: UserPreferences
    private var token: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.add_media)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userPreferences = UserPreferences.getInstance(applicationContext)

        lifecycleScope.launch {
            token = userPreferences.getToken().first()
        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.buttonAdd.setOnClickListener { uploadMedia() }
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
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun uploadMedia() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            val description = binding.edAddDescription.text.toString()
            val title = binding.edAddTitle.text.toString()

            showLoading(true)

            val titleRequestBody = title.toRequestBody("text/plain".toMediaType())
            val descriptionRequestBody = description.toRequestBody("text/plain".toMediaType())
            val imageRequestBody = imageFile.asRequestBody("image/jpeg".toMediaType())

            val imagePart = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                imageRequestBody
            )

            lifecycleScope.launch {
                try {
                    val apiService = ApiConfig.getApiService()
                    val successResponse = apiService.uploadMedia("Bearer $token", imagePart, titleRequestBody, descriptionRequestBody)
                    showToast(successResponse.message!!)
                    showLoading(false)

                    if (successResponse.message == "Post created successfully!") {
                        finish()
                    }
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, MediaResponse::class.java)
                    showToast(errorResponse.message!!)
                    showLoading(false)
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun showLoading(isLoading: Boolean) {
        binding.ProgressBarAddMedia.visibility = if (isLoading) View.VISIBLE else View.GONE
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