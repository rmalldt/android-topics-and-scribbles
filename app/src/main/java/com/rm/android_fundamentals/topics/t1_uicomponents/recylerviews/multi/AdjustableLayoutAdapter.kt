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

class AdjustableLayoutAdapter(
    private val layoutManagerType: String
) : RecyclerView.Adapter<AdjustableLayoutAdapter.LinearViewHolder>() {

    private var itemList = emptyList<LinearItem>()

    override fun getItemViewType(position: Int): Int {
        return when (layoutManagerType) {
            "linear" -> 0
            "grid"-> 1
            else -> 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinearViewHolder {
        when (viewType) {
            0 -> {
                val binding = RvItemLinearBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return LinearViewHolder(binding, layoutManagerType)
            }

            1 -> {
                val binding = RvItemGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return LinearViewHolder(binding, layoutManagerType)
            }

            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: LinearViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context, R.anim.rv_anim))
    }

    override fun getItemCount(): Int = itemList.size

    fun addItems(items: List<LinearItem>) {
        itemList = items
        notifyChanges(itemList, items)
    }

    class LinearViewHolder(val binding: ViewBinding, val layoutManagerType: String) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LinearItem) {
            if (layoutManagerType == "grid") {
                val gridBinding = binding as RvItemGridBinding
                gridBinding.txtRvGrid.text = binding.txtRvGrid.context.getString(R.string.grid_item, item.text.substring(12))
            } else {
                val linearBinding = binding as RvItemLinearBinding
                linearBinding.txtRvLinear.text = item.text
            }
        }
    }

    private fun notifyChanges(oldList: List<LinearItem>, newList: List<LinearItem>) {
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
