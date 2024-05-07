package com.rm.android_fundamentals.topics.t1_uicomponents.recylerviews.utils

import androidx.recyclerview.widget.DiffUtil

class RvDiffUtil<T>(
    private val oldList: List<T>,
    private val newList: List<T>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] === newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

}