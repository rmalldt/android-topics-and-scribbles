package com.rm.android_fundamentals.topics.t2_appnavigation.s2_viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rm.android_fundamentals.databinding.FragmentChildBinding

class ChildFragment : Fragment() {

    private var _binding: FragmentChildBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf {
            it.containsKey(ARG_OBJ).apply {
                binding.txtObj.text = it.getInt(ARG_OBJ).toString()
            }
        }
    }
}

private const val ARG_OBJ = "obj"