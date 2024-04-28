package com.rm.android_fundamentals.base

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rm.android_fundamentals.base.model.NavDest
import com.rm.android_fundamentals.base.model.NavDrawerSection
import com.rm.android_fundamentals.base.model.NavDrawerTopic
import com.rm.android_fundamentals.databinding.ExpandableRvItemBinding
import com.rm.android_fundamentals.utils.dpToPx
import com.rm.android_fundamentals.utils.setGone
import com.rm.android_fundamentals.utils.setVisible

class ExpandableTopicAdapter(
    private val navDrawerData: List<NavDest>,
    private val onSectionClicked: (NavDrawerSection) -> Unit,
    private val indent: Int = PADDING_START_PX
) : RecyclerView.Adapter<ExpandableTopicAdapter.TopicViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopicViewHolder {
        val binding = ExpandableRvItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TopicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopicViewHolder , position: Int) {
        val item = navDrawerData[position]

        holder.binding.run {
            when (item.data) {
                is NavDrawerTopic -> {
                    txtExpandableTitle.text = item.data.title
                    txtExpandableTitle.setPadding(indent, PADDINGS_TOP_PX, PADDING_END_PX, PADDINGS_BOTTOM_PX )
                    val angle = if (item.data.isExpanded) {
                        rvSections.setVisible()
                        180F
                    } else {
                        rvSections.setGone()
                        0F
                    }
                    iconArrow.animate().rotation(angle).setInterpolator(AccelerateInterpolator())

                    val paddingInPx = dpToPx(20, txtExpandableTitle.context)
                    rvSections.apply {
                        adapter = ExpandableTopicAdapter(item.data.sections, onSectionClicked, indent + paddingInPx)
                        layoutManager = LinearLayoutManager(holder.binding.root.context)
                    }

                    layoutExpandable.setOnClickListener {
                        item.data.isExpanded = !item.data.isExpanded
                        notifyItemChanged(holder.adapterPosition)
                    }
                }

                is NavDrawerSection -> {
                    txtExpandableTitle.text = item.data.title
                    txtExpandableTitle.setPadding(indent, PADDINGS_TOP_PX, PADDING_END_PX, PADDINGS_BOTTOM_PX )
                    iconArrow.setGone()
                    layoutExpandable.setOnClickListener {
                        onSectionClicked(item.data)
                    }
                }

                else -> {}
            }
        }
    }

    override fun getItemCount(): Int = navDrawerData.size

    class TopicViewHolder(val binding: ExpandableRvItemBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        const val PADDING_START_PX = 72
        const val PADDING_END_PX = 0
        const val PADDINGS_TOP_PX = 18
        const val PADDINGS_BOTTOM_PX = 18
    }
}

