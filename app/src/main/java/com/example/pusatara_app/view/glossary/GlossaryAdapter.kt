package com.example.pusatara_app.view.glossary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pusatara_app.R
import com.example.pusatara_app.data.api.response.PatternsItem
import com.example.pusatara_app.databinding.ItemGlossaryBinding

class GlossaryAdapter : ListAdapter<PatternsItem, GlossaryAdapter.GlossaryViewHolder>(GlossaryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GlossaryViewHolder {
        val binding = ItemGlossaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GlossaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GlossaryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class GlossaryViewHolder(private val binding: ItemGlossaryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PatternsItem) {
            binding.tvGlossaryName.text = item.name

            Glide.with(itemView)
                .load(item.image)
                .placeholder(R.drawable.img_default)
                .into(binding.ivGlossary)
        }
    }

    class GlossaryDiffCallback : DiffUtil.ItemCallback<PatternsItem>() {
        override fun areItemsTheSame(oldItem: PatternsItem, newItem: PatternsItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PatternsItem, newItem: PatternsItem): Boolean {
            return oldItem == newItem
        }
    }
}