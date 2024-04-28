package com.rm.android_fundamentals.topics.t1_uicomponents.layouts

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.databinding.FragmentLayoutsBinding
import com.rm.android_fundamentals.topics.t2_appnavigation.s1_fragments.fragmentprogrammatic.OneFragment
import com.rm.android_fundamentals.topics.t2_appnavigation.s1_fragments.fragmentprogrammatic.ThreeFragment
import com.rm.android_fundamentals.topics.t2_appnavigation.s1_fragments.fragmentprogrammatic.TwoFragment

class LayoutsFragment : Fragment() {

    private var _binding: FragmentLayoutsBinding? = null
    private val binding get() = _binding!!

    private var initialIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLayoutsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPagerTab()
    }

    private fun setViewPagerTab() {
        val fragments = listOf(
            LinearLayoutFragment(),
            RelativeLayoutFragment(),
            ConstraintLayoutFragment()
        )

        binding.viewPager.adapter = LayoutsFragmentAdapter(fragments, childFragmentManager, lifecycle)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()

        binding.run {
            btnPrev.isEnabled = false

            btnNext.setOnClickListener {
                binding.tabLayout.getTabAt(initialIndex + 1)?.select()
                initialIndex++
                controlTabNavigationButtons(fragments.size - 1)
            }

            btnPrev.setOnClickListener {
                binding.tabLayout.getTabAt(initialIndex - 1)?.select()
                initialIndex--
                controlTabNavigationButtons(fragments.size)
            }
        }
    }

    private fun controlTabNavigationButtons(lastItem: Int) {
        binding.run {
            when (initialIndex) {
                0 -> btnPrev.isEnabled = false

                lastItem -> btnNext.isEnabled = false

                else -> {
                    btnNext.isEnabled = true
                    btnPrev.isEnabled = true
                }
            }
        }
    }
}