package com.example.pusatara_app.view.scan

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.pusatara_app.data.di.UserPreferences
import com.example.pusatara_app.databinding.ActivityScanOutputBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class ScanOutputActivity : AppCompatActivity() {
    private lateinit var binding : ActivityScanOutputBinding
    private lateinit var adapter: ScanOutputAdapter
    private lateinit var userPreferences: UserPreferences
    private lateinit var viewModel: UploadScanViewModel
    private var token: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanOutputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Scan output"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userPreferences = UserPreferences.getInstance(applicationContext)
        lifecycleScope.launch {
            token = userPreferences.getToken().first()
        }

        viewModel = ViewModelProvider(this)[UploadScanViewModel::class.java]
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