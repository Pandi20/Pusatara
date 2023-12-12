package com.example.pusatara_app.view.scan

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanOutputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Scan Output"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this)[UploadScanViewModel::class.java]

        userPreferences = UserPreferences.getInstance(applicationContext)
        lifecycleScope.launch {
            token = userPreferences.getToken().first()
        }

        adapter = ScanOutputAdapter()
        binding.rvResultScan.layoutManager = LinearLayoutManager(this@ScanOutputActivity)
        binding.rvResultScan.adapter = adapter

        viewModel.scanResults.observe(this@ScanOutputActivity) { scanResults ->
            Log.d("scanResults", "Scan Results: $scanResults")
            if (scanResults.isNotEmpty()) {
                adapter.submitList(scanResults)
            }
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