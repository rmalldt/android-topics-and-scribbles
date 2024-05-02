package com.rm.android_fundamentals.topics.t1_uicomponents.viewpagers

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.databinding.RecyclerviewItemBinding
import com.rm.android_fundamentals.databinding.RvItemBinding

class RvAdapter : RecyclerView.Adapter<RvAdapter.RvViewHolder>() {

    var itemList = emptyList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
        val binding = RvItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context, R.anim.rv_anim))
    }

    override fun getItemCount(): Int = itemList.size

    class RvViewHolder(val binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
        }
    }

    fun addItems(items: List<String>) {
        itemList = items
        notifyChanges(itemList, items)
    }

    private fun notifyChanges(oldList: List<String>, newList: List<String>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = oldList.size
            override fun getNewListSize() = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldList[oldItemPosition] == newList[newItemPosition]

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldList[oldItemPosition] == newList[newItemPosition]
        })
        diff.dispatchUpdatesTo(this)
    }
}
