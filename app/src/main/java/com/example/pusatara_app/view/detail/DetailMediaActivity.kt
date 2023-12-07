package com.example.pusatara_app.view.detail

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.pusatara_app.R
import com.example.pusatara_app.data.api.retrofit.ApiConfig
import com.example.pusatara_app.data.di.UserPreferences
import com.example.pusatara_app.databinding.ActivityDetailMediaBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

@Suppress("DEPRECATION")
class DetailMediaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMediaBinding
    private lateinit var userPreferences: UserPreferences
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.media_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userPreferences = UserPreferences.getInstance(this)

        val mediaId = intent.getIntExtra("media_id", -1)

        if (mediaId != -1) {
            lifecycleScope.launch {
                try {
                    if (token == null) {
                        token = userPreferences.getToken().first()
                    }

                    if (token != null) {
                        val apiService = ApiConfig.getApiService()

                        val response = apiService.getMediaById("Bearer $token", mediaId)
                        Log.d("DetailMediaActivity", "Response: $response")

                        if (response.id != null) {
                            binding.tvDetailUsername.text = response.user?.username ?: "Unknown"
                            binding.tvDetailTitle.text = response.title
                            binding.tvDetailDescription.text = response.content
                            Glide.with(this@DetailMediaActivity)
                                .load(response.imageUrl)
                                .into(binding.ivDetailPhoto)
                        } else {
                            Log.e("DetailMediaActivity", "Data media kosong")
                        }
                    } else {
                        Log.e("DetailMediaActivity", "Token kosong")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    showErrorDialogFromException(e)
                }
            }
        } else {
            Log.e("DetailMediaActivity", "mediaId invalid")
        }
    }

    private fun showErrorDialogFromException(e: Exception) {
        if (!isFinishing) {
            val errorMessage = when (e) {
                is HttpException -> {
                    if (e.code() == 401) {
                        getString(R.string.unauthorized)
                    } else {
                        "${e.message}"
                    }
                }
                is IOException -> getString(R.string.error_internet)
                else -> "${e.message}"
            }

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.error))
                .setMessage(errorMessage)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
