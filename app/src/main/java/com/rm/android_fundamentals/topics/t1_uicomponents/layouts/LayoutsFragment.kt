package com.rm.android_fundamentals.topics.t1_uicomponents.layouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.rm.android_fundamentals.databinding.FragmentLayoutsBinding

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
                binding.tabLayout.getTabAt(++initialIndex)?.select()
                controlTabNavigationButtons(fragments.size - 1)
            }

            btnPrev.setOnClickListener {
                binding.tabLayout.getTabAt(--initialIndex)?.select()
                controlTabNavigationButtons(fragments.size - 1)
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