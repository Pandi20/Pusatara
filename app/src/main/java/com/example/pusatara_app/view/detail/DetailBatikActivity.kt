package com.example.pusatara_app.view.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.pusatara_app.R
import com.example.pusatara_app.data.api.response.BatikPatternsItem
import com.example.pusatara_app.databinding.ActivityDetailBatikBinding

@Suppress("DEPRECATION")
class DetailBatikActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBatikBinding

    companion object {
        const val DETAIL_BATIK = "detail_batik"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBatikBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.detail_glossary_bar_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val batikItem = intent.getSerializableExtra(DETAIL_BATIK) as? BatikPatternsItem
        if (batikItem != null) {
            binding.tvDetailName.text = batikItem.name
            binding.tvDetailDescription.text = batikItem.desc
            Glide.with(this)
                .load(batikItem.image)
                .placeholder(R.drawable.img_default)
                .into(binding.ivDetailPhoto)
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