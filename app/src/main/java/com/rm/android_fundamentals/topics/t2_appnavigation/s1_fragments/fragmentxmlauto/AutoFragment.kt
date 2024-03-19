package com.rm.android_fundamentals.topics.t2_appnavigation.s1_fragments.fragmentxmlauto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rm.android_fundamentals.databinding.FragmentAutoBinding
import com.rm.android_fundamentals.utils.toast

class AutoFragment : Fragment() {

    private var _binding: FragmentAutoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAutoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromActivity()
    }

    private fun getDataFromActivity() {
        val dataFromActivity = requireArguments().getString("data")
        if (dataFromActivity != null) {
            requireContext().toast("Data: $dataFromActivity")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}