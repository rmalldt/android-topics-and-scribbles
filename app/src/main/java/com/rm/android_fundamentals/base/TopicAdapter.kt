package com.rm.android_fundamentals.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rm.android_fundamentals.databinding.RecyclerviewItemBinding

class TopicAdapter(
    private val topics: List<Topic>,
    private val onTopicClicked: (Topic) -> Unit
) : RecyclerView.Adapter<TopicAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = topics.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textRvItem.apply {
            text = topics[position].description

            setOnClickListener {
                onTopicClicked(topics[position])
            }
        }
    }

    class ViewHolder(val binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root)
}