package com.rm.android_fundamentals.topics.t2_appnavigation.s2_viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.rm.android_fundamentals.databinding.FragmentCollectionBinding

class CollectionFragment : Fragment() {

    private var _binding: FragmentCollectionBinding? = null
    private val binding get() = _binding!!

    private lateinit var collectionFragmentAdapter: CollectionFragmentAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPagerTabLayout()
    }


    private fun setViewPagerTabLayout() {
        collectionFragmentAdapter = CollectionFragmentAdapter(this)
        viewPager = binding.pager
        viewPager.adapter = collectionFragmentAdapter

        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "OBJECT ${position + 1}"
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}