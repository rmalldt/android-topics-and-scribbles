package com.rm.android_fundamentals.topics.t2_appnavigation.s1_fragments.fragmentprogrammatic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.databinding.FragmentTwoBinding


class TwoFragment : Fragment() {

    private var _binding: FragmentTwoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTwoBinding.inflate(inflater, container, false)
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