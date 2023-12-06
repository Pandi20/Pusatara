package com.example.pusatara_app.view.media

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pusatara_app.data.api.response.DataItem
import com.example.pusatara_app.databinding.ItemMediaBinding
import com.example.pusatara_app.view.detail.DetailActivity

class MediaAdapter : PagingDataAdapter<DataItem, MediaAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    class ListViewHolder(binding: ItemMediaBinding) : RecyclerView.ViewHolder(binding.root) {
        private val cardImg: View = binding.itemMediaView
        private val mediaImg: ImageView = binding.imgMediaPhoto
        private val mediaAvatar: ImageView = binding.ivAvatar
        private val mediaUsername: TextView = binding.tvMediaUsername
        private val titleMedia: TextView = binding.tvMediaTitle
        private val mediaDescription: TextView = binding.tvMediaDescription


        fun bind(media: DataItem) {
            Glide.with(itemView.context)
                .load(media.imageUrl)
                .into(mediaImg)
            mediaUsername.text = media.user?.username ?: "Unknown"
            titleMedia.text = media.title
            mediaDescription.text = media.content

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("media_id", media.id)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(cardImg, "card_photo"),
                        Pair(mediaImg, "media_photo"),
                        Pair(mediaAvatar, "avatar"),
                        Pair(mediaUsername, "name"),
                        Pair(titleMedia, "title"),
                        Pair(mediaDescription, "description"),
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}