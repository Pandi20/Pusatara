package com.example.pusatara_app.view.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pusatara_app.R
import com.example.pusatara_app.data.api.response.SearchResponseItem
import com.example.pusatara_app.databinding.ItemGlossaryBinding
import com.example.pusatara_app.view.detail.DetailSearchActivity
import java.io.Serializable

class SearchAdapter : ListAdapter<SearchResponseItem, SearchAdapter.SearchViewHolder>(GlossaryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding =
            ItemGlossaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentItem = getItem(position)

        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailSearchActivity::class.java)
            intent.putExtra(DetailSearchActivity.DETAIL_SEARCH, currentItem as Serializable)
            holder.itemView.context.startActivity(intent)
        }
    }

    class SearchViewHolder(private val binding: ItemGlossaryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchResponseItem) {
            binding.tvGlossaryName.text = item.name

            Glide.with(itemView)
                .load(item.image)
                .placeholder(R.drawable.img_default)
                .into(binding.ivGlossary)
        }
    }

    class GlossaryDiffCallback : DiffUtil.ItemCallback<SearchResponseItem>() {
        override fun areItemsTheSame(oldItem: SearchResponseItem, newItem: SearchResponseItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SearchResponseItem, newItem: SearchResponseItem): Boolean {
            return oldItem == newItem
        }
    }
}