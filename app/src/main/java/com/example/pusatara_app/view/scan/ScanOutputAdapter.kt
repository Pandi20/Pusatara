package com.example.pusatara_app.view.scan

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
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
            binding.tvResultName.text = scanOutput.className

            val probabilityPercent = (scanOutput.probability?.times(100))?.toInt() ?: 0
            binding.tvResultPercent.text = "$probabilityPercent%"

            when {
                probabilityPercent < 33 -> binding.tvResultPercent.setTextColor(Color.RED)
                probabilityPercent in 34..66 -> binding.tvResultPercent.setTextColor(Color.YELLOW)
                probabilityPercent in 67..100 -> binding.tvResultPercent.setTextColor(Color.GREEN)
                else -> binding.tvResultPercent.setTextColor(Color.BLACK)
            }
        }
    }

    fun setData(newData: List<ScanResponseItem>) {
        submitList(newData)
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