package com.rm.android_fundamentals.topics.t2_appnavigation.s2_viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CollectionFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val fragment = ChildFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_OBJ, position + 1)
        }
        return fragment
    }
}

private const val ARG_OBJ = "obj"