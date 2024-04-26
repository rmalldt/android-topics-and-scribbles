package com.rm.android_fundamentals.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rm.android_fundamentals.databinding.RecyclerviewItemBinding

class SubTopicAdapter(
    private val topic: Topic,
    private val onItemClicked: (SubTopic) -> Unit
) : RecyclerView.Adapter<SubTopicAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textRvItem.apply {
            text = topic.subTopics[position].description

            setOnClickListener {
                onItemClicked(topic.subTopics[position])
            }
        }
    }

    override fun getItemCount() = topic.subTopics.size

    class ViewHolder(val binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root)
}