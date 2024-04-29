package com.rm.android_fundamentals.topics.t1_uicomponents.layouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.databinding.FragmentLinearLayoutBinding
import com.rm.android_fundamentals.utils.toggleColor

class LinearLayoutFragment : Fragment() {

    private var _binding: FragmentLinearLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLinearLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var btnClicked = false
        binding.btnLinear.setOnClickListener {
            btnClicked = !btnClicked
            it.toggleColor(btnClicked, R.color.colorAccent, R.color.colorPrimaryDark, requireContext())
        }
    }
}