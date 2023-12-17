package com.example.pusatara_app.view.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pusatara_app.R
import com.example.pusatara_app.data.api.response.SongketPatternsItem
import com.example.pusatara_app.databinding.ItemBatikSongketBinding
import com.example.pusatara_app.view.detail.DetailSongketActivity
import java.io.Serializable

class SongketAdapter : ListAdapter<SongketPatternsItem, SongketAdapter.SongketViewHolder>(GlossaryDiffCallback())  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongketViewHolder {
        val binding = ItemBatikSongketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongketViewHolder, position: Int) {
        val currentItem = getItem(position)

        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailSongketActivity::class.java)
            intent.putExtra(DetailSongketActivity.DETAIL_SONGKET, currentItem as Serializable)
            holder.itemView.context.startActivity(intent)
        }
    }

    class SongketViewHolder(private val binding: ItemBatikSongketBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SongketPatternsItem) {
            binding.tvTypeName.text = item.name
            binding.tvTypeDesc.text = item.desc

            Glide.with(itemView)
                .load(item.image)
                .placeholder(R.drawable.img_default)
                .into(binding.ivType)
        }
    }

    class GlossaryDiffCallback : DiffUtil.ItemCallback<SongketPatternsItem>() {
        override fun areItemsTheSame(oldItem: SongketPatternsItem, newItem: SongketPatternsItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SongketPatternsItem, newItem: SongketPatternsItem): Boolean {
            return oldItem == newItem
        }
    }
}