package com.example.pusatara_app.view.scan

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pusatara_app.data.api.response.ScanResponse
import com.example.pusatara_app.databinding.ItemScanOutputBinding

class ScanOutputAdapter : ListAdapter<ScanResponse, ScanOutputAdapter.ScanResultViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemScanOutputBinding.inflate(inflater, parent, false)
        return ScanResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScanResultViewHolder, position: Int) {
        val scanResult = getItem(position)
        holder.bind(scanResult)
    }

    class ScanResultViewHolder(private val binding: ItemScanOutputBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(scanResult: ScanResponse) {
            binding.tvResultName.text = scanResult.className
            val probability = scanResult.probability

            // Set color based on probability criteria
            val textColor = when {
                probability < 33 -> android.R.color.holo_red_light
                probability in 34.0..66.0 -> android.R.color.holo_orange_light
                probability in 67.0..100.0 -> android.R.color.holo_green_light
                else -> android.R.color.black
            }
            binding.tvResultPercent.setTextColor(ContextCompat.getColor(itemView.context, textColor))
            binding.tvResultPercent.text = "$probability%"
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ScanResponse>() {
        override fun areItemsTheSame(oldItem: ScanResponse, newItem: ScanResponse): Boolean {
            return oldItem.className == newItem.className
        }

        override fun areContentsTheSame(oldItem: ScanResponse, newItem: ScanResponse): Boolean {
            return oldItem == newItem
        }
    }
}