package com.example.pusatara_app.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pusatara_app.R
import com.example.pusatara_app.data.api.response.PatternsItem
import com.example.pusatara_app.databinding.ItemBatikSongketBinding

class HomeAdapter(private val homeItems: List<PatternsItem>) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            ItemBatikSongketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(homeItems[position])
    }

    override fun getItemCount(): Int = homeItems.size

    class HomeViewHolder(private val binding: ItemBatikSongketBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PatternsItem) {
            binding.tvBatikName.text = item.name
            binding.tvBatikDesc.text = item.desc

            Glide.with(itemView)
                .load(item.image)
                .placeholder(R.drawable.img_default)
                .into(binding.ivBatik)
        }
    }
}