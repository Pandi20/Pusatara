package com.example.pusatara_app.view.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.pusatara_app.R
import com.example.pusatara_app.data.api.response.SearchResponseItem
import com.example.pusatara_app.databinding.ActivityDetailSearchBinding

@Suppress("DEPRECATION")
class DetailSearchActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailSearchBinding

    companion object {
        const val DETAIL_SEARCH = "detail_search"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.detail_glossary_bar_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val searchItem = intent.getSerializableExtra(DETAIL_SEARCH) as? SearchResponseItem
        if (searchItem != null) {
            binding.tvDetailName.text = searchItem.name
            binding.tvDetailDescription.text = searchItem.desc
            Glide.with(this)
                .load(searchItem.image)
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