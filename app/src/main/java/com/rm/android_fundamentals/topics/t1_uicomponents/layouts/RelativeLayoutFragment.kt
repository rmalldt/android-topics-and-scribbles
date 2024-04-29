package com.rm.android_fundamentals.topics.t1_uicomponents.layouts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.databinding.FragmentLinearLayoutBinding
import com.rm.android_fundamentals.databinding.FragmentRelativeLayoutBinding
import com.rm.android_fundamentals.utils.toggleColor

class RelativeLayoutFragment : Fragment() {

    private var _binding: FragmentRelativeLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRelativeLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var btnClicked = false
        binding.btnRelative.setOnClickListener {
            btnClicked = !btnClicked
            it.toggleColor(btnClicked, R.color.colorAccent, R.color.colorPrimaryDark, requireContext())
        }
    }
}