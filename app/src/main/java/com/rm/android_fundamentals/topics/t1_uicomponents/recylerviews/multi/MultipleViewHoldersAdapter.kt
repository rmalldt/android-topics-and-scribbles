package com.rm.android_fundamentals.topics.t1_uicomponents.recylerviews.multi

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.databinding.RvItemGridBinding
import com.rm.android_fundamentals.databinding.RvItemLinearBinding
import java.lang.IllegalArgumentException

class MultipleViewHoldersAdapter : RecyclerView.Adapter<MultipleViewHoldersAdapter.BaseViewHolder<RvItem>>() {

    var itemList = emptyList<RvItem>()

    override fun getItemViewType(position: Int): Int {
        return itemList[position].getItemType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<RvItem> {
        when (viewType) {
            RvItemType.Linear.ordinal -> {
                val binding = RvItemLinearBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return LinearViewHolder(binding)
            }

            RvItemType.Grid.ordinal -> {
                val binding = RvItemGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return GridViewHolder(binding)
            }

            else -> throw IllegalArgumentException()
        }
    }


    override fun onBindViewHolder(holder: BaseViewHolder<RvItem>, position: Int) {
        val item = itemList[position]
        holder.bind(item)
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context, R.anim.rv_anim))
    }

    override fun getItemCount(): Int = itemList.size

    fun addItems(items: List<RvItem>) {
        itemList = items
        notifyChanges(itemList, items)
    }

    class LinearViewHolder(val binding: RvItemLinearBinding) : BaseViewHolder<RvItem>(binding) {
        override fun bind(item: RvItem) {
            val linearItem =  item as LinearItem
            binding.txtRvLinear.text = item.text
        }
    }

    class GridViewHolder(val binding: RvItemGridBinding) : BaseViewHolder<RvItem>(binding) {
        override fun bind(item: RvItem) {
            val gridItem = item as GridItem
            binding.txtRvGrid.text = item.text
        }
    }

    abstract class BaseViewHolder<T>(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(item: T)
    }


    private fun notifyChanges(oldList: List<RvItem>, newList: List<RvItem>) {
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


