package com.rm.android_fundamentals.topics.t2_appnavigation.s1_fragments.fragmentprogrammatic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.databinding.FragmentThreeBinding

class ThreeFragment : Fragment() {

    private var _binding: FragmentThreeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThreeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
    }

    private fun setToolbar() {
        binding.toolbar.apply {
            setNavigationIcon(R.drawable.arrow_back_white)
            setNavigationOnClickListener { requireActivity().supportFragmentManager.popBackStack() }
        }
    }
}