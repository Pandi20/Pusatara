package com.example.pusatara_app.view.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pusatara_app.databinding.ActivityUploadScanBinding

class UploadScanActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUploadScanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}