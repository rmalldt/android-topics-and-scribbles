package com.rm.android_fundamentals.topics.t1_uicomponents.recylerviews.simple

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.databinding.RvItemLinearBinding
import com.rm.android_fundamentals.topics.t1_uicomponents.recylerviews.RvDiffUtil
import com.rm.android_fundamentals.utils.toggleColor

class SimpleRvAdapter(
    private val onRowItemClicked: (itemId: String) -> Unit
) : RecyclerView.Adapter<SimpleRvAdapter.SimpleRvViewHolder>()  {

    var dataList = emptyList<SimpleRvItem>()
        set(value) {
            val diffUtil = RvDiffUtil(dataList, value)
            val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
            field = value
            diffUtilResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimpleRvViewHolder {
        val binding = RvItemLinearBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SimpleRvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimpleRvViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item, onRowItemClicked)
    }

    override fun getItemCount(): Int = dataList.size

    class SimpleRvViewHolder(val binding: RvItemLinearBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SimpleRvItem, onRowItemClicked: (itemId: String) -> Unit) {
            binding.txtRvLinear.run {
                text = item.id

                setOnClickListener {
                    onRowItemClicked(item.id)
                }
            }

            binding.btnSwitchLinear.run {
                // Check the state of current item before setting button text and color
                if (item.switchState) {
                    setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
                    text = context.getString(R.string.on)
                } else {
                    setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                    text = context.getString(R.string.off)
                }

                setOnClickListener {
                    item.switchState = !item.switchState
                    toggleColor(item.switchState, R.color.colorAccent, R.color.colorPrimaryDark, context)
                    text = if (item.switchState) context.getString(R.string.on) else context.getString(R.string.off)
                }
            }

        }
    }
}