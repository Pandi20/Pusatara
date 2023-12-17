package com.example.pusatara_app.view.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pusatara_app.R
import com.example.pusatara_app.data.api.response.BatikPatternsItem
import com.example.pusatara_app.databinding.ItemBatikSongketBinding
import com.example.pusatara_app.view.detail.DetailBatikActivity
import java.io.Serializable

class BatikAdapter : ListAdapter<BatikPatternsItem, BatikAdapter.BatikViewHolder>(GlossaryDiffCallback())  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BatikViewHolder {
        val binding = ItemBatikSongketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BatikViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BatikViewHolder, position: Int) {
        val currentItem = getItem(position)

        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailBatikActivity::class.java)
            intent.putExtra(DetailBatikActivity.DETAIL_BATIK, currentItem as Serializable)
            holder.itemView.context.startActivity(intent)
        }
    }

    class BatikViewHolder(private val binding: ItemBatikSongketBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BatikPatternsItem) {
            binding.tvTypeName.text = item.name
            binding.tvTypeDesc.text = item.desc

            Glide.with(itemView)
                .load(item.image)
                .placeholder(R.drawable.img_default)
                .into(binding.ivType)
        }
    }

    class GlossaryDiffCallback : DiffUtil.ItemCallback<BatikPatternsItem>() {
        override fun areItemsTheSame(oldItem: BatikPatternsItem, newItem: BatikPatternsItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BatikPatternsItem, newItem: BatikPatternsItem): Boolean {
            return oldItem == newItem
        }
    }
}