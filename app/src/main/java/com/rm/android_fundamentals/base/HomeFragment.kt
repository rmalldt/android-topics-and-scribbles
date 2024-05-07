package com.rm.android_fundamentals.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rm.android_fundamentals.databinding.FragmentMainExpandableTopicBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentMainExpandableTopicBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainExpandableTopicBinding.inflate(inflater, container, false)
        return binding.root
    }
}