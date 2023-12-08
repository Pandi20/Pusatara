package com.example.pusatara_app.view.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.pusatara_app.databinding.ActivityDetailGlossaryBinding

@Suppress("DEPRECATION")
class DetailGlossaryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailGlossaryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGlossaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Glossary Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}