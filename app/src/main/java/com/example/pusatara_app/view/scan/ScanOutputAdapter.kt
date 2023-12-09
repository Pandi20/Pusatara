package com.example.pusatara_app.view.scan

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pusatara_app.data.api.response.ScanResponseItem
import com.example.pusatara_app.databinding.ItemScanOutputBinding

class ScanOutputAdapter(private val scanOutput: List<ScanResponseItem>) :
    RecyclerView.Adapter<ScanOutputAdapter.ScanOutputViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanOutputViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemScanOutputBinding.inflate(inflater, parent, false)
        return ScanOutputViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScanOutputViewHolder, position: Int) {
        holder.bind(scanOutput[position])
    }

    override fun getItemCount(): Int = scanOutput.size

    class ScanOutputViewHolder(private val binding: ItemScanOutputBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(scanOutput: ScanResponseItem) {
            binding.tvBatikName.text = scanOutput.className

            val probabilityPercent = (scanOutput.probability as? Double)?.times(100)?.toInt() ?: 0
            binding.tvResultPercent.text = "$probabilityPercent%"

            when {
                probabilityPercent < 35 -> binding.tvResultPercent.setTextColor(Color.RED)
                probabilityPercent in 35..65 -> binding.tvResultPercent.setTextColor(Color.YELLOW)
                probabilityPercent in 65..100 -> binding.tvResultPercent.setTextColor(Color.GREEN)
                else -> binding.tvResultPercent.setTextColor(Color.BLACK)
            }
        }
    }
}