package com.example.pusatara_app.view.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.pusatara_app.R
import com.example.pusatara_app.data.api.response.SongketPatternsItem
import com.example.pusatara_app.databinding.ActivityDetailSongketBinding

@Suppress("DEPRECATION")
class DetailSongketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailSongketBinding

    companion object {
        const val DETAIL_SONGKET = "detail_songket"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSongketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.detail_glossary_bar_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val songketItem = intent.getSerializableExtra(DETAIL_SONGKET) as? SongketPatternsItem
        if (songketItem != null) {
            binding.tvDetailName.text = songketItem.name
            binding.tvDetailDescription.text = songketItem.desc
            Glide.with(this)
                .load(songketItem.image)
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