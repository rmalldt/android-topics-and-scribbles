package com.rm.android_fundamentals.topics.t1_uicomponents.layouts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.databinding.FragmentConstraintLayoutBinding
import com.rm.android_fundamentals.databinding.FragmentLinearLayoutBinding
import com.rm.android_fundamentals.utils.toast
import com.rm.android_fundamentals.utils.toggleColor

class ConstraintLayoutFragment : Fragment() {

    private var _binding: FragmentConstraintLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConstraintLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSend.setOnClickListener {
            validateMessage()
            requireContext().toast("Message sent")
        }

        binding.btnCancel.setOnClickListener {
            requireContext().toast("Cancelled")
        }
    }

    private fun validateMessage() = with(binding) {
        if (txtTo.text.isEmpty() || txtSubject.text.isEmpty() || txtMessage.text.isEmpty()) {
            requireContext().toast("Please input all the fields")
        }
    }
}