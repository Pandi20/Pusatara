package com.example.pusatara_app.view.home

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pusatara_app.databinding.ActivityScanOutputBinding

@Suppress("DEPRECATION")
class ScanOutputActivity : AppCompatActivity() {
    private lateinit var binding : ActivityScanOutputBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanOutputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Scan output"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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