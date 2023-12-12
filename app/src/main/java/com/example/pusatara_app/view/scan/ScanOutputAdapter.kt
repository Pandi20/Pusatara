package com.example.pusatara_app.view.scan

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pusatara_app.data.api.response.ScanResponseItem
import com.example.pusatara_app.databinding.ItemScanOutputBinding

class ScanOutputAdapter :
    ListAdapter<ScanResponseItem, ScanOutputAdapter.ScanOutputViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanOutputViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemScanOutputBinding.inflate(inflater, parent, false)
        return ScanOutputViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScanOutputViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ScanOutputViewHolder(private val binding: ItemScanOutputBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(scanOutput: ScanResponseItem) {
            Log.d("ScanOutputAdapter", "ClassName: ${scanOutput.className}, Probability: ${scanOutput.probability}")
            binding.tvResultName.text = scanOutput.className

            val probabilityPercent = scanOutput.probability
            binding.tvResultPercent.text = "$probabilityPercent%"

            val textColor = when {
                probabilityPercent < 33 -> android.R.color.holo_red_light
                probabilityPercent in 34.0..66.0 -> android.R.color.holo_orange_light
                probabilityPercent in 67.0..100.0 -> android.R.color.holo_green_light
                else -> android.R.color.black
            }
            binding.tvResultPercent.setTextColor(ContextCompat.getColor(itemView.context, textColor))
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<ScanResponseItem>() {
        override fun areItemsTheSame(oldItem: ScanResponseItem, newItem: ScanResponseItem): Boolean {
            return oldItem.className == newItem.className
        }

        override fun areContentsTheSame(oldItem: ScanResponseItem, newItem: ScanResponseItem): Boolean {
            return oldItem == newItem
        }
    }
}