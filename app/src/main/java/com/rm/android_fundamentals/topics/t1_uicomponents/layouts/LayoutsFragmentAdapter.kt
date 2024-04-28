package com.rm.android_fundamentals.topics.t1_uicomponents.layouts

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.lifecycle.Lifecycle

class LayoutsFragmentAdapter(
    private val fragments: List<Fragment>,
    private val manager: FragmentManager,
    private val lifecycle: Lifecycle
) : FragmentStateAdapter(manager, lifecycle) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}