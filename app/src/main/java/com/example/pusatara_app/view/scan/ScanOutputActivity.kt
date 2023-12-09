package com.example.pusatara_app.view.scan

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pusatara_app.data.api.response.ScanResponse
import com.example.pusatara_app.databinding.ActivityScanOutputBinding

@Suppress("DEPRECATION")
class ScanOutputActivity : AppCompatActivity() {
    private lateinit var binding : ActivityScanOutputBinding
    private lateinit var adapter: ScanOutputAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanOutputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Scan output"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        val scanResponse: ScanResponse? = intent.getParcelableExtra("scanResponse")
//        showScanResults(scanResponse)
    }

    private fun showScanResults(scanResponse: ScanResponse?) {
        if (scanResponse != null) {
            val scanResults = scanResponse.scanResponse
            if (scanResults.isNotEmpty()) {
                // Initialize and set up the RecyclerView
                adapter = ScanOutputAdapter(scanResults)
                binding.rvResultScan.layoutManager = LinearLayoutManager(this)
                binding.rvResultScan.adapter = adapter
            }
        } else {
            showToast("Failed to retrieve scan results.")
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