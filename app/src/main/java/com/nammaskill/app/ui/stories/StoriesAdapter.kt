package com.nammaskill.app.ui.stories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nammaskill.app.data.SuccessStory
import com.nammaskill.app.databinding.ItemStoryBinding

class StoriesAdapter : ListAdapter<SuccessStory, StoriesAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<SuccessStory>() {
        override fun areItemsTheSame(oldItem: SuccessStory, newItem: SuccessStory) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: SuccessStory, newItem: SuccessStory) = oldItem == newItem
    }

    class VH(val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.binding.storyTitle.text = item.title
        holder.binding.storyBody.text = item.body
    }
}

