package com.example.pusatara_app.view.scan

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
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
            binding.tvResultPercent.text = "Probability: ${scanResult.probability}"
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