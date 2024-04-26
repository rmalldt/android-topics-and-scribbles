package com.rm.android_fundamentals.topics.t0

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.rm.android_fundamentals.base.SubTopicAdapter
import com.rm.android_fundamentals.base.Topic
import com.rm.android_fundamentals.databinding.FragmentMainExpandableTopicBinding
import com.rm.android_fundamentals.utils.setGone
import com.rm.android_fundamentals.utils.setVisible
import timber.log.Timber

class MainExpandableTopicFragment : Fragment() {

    private var _binding: FragmentMainExpandableTopicBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainExpandableTopicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        render()
    }

    private fun render() {
        val topic = getTopic()
        Timber.d("topic: $topic")

        when (topic) {
            null -> {
                binding.rvTopicFragment.setGone()
                binding.txtMainTitle.setVisible()
                binding.txtMainDescription.setVisible()
            }
            else -> {
                binding.rvTopicFragment.setVisible()
                binding.txtMainTitle.setGone()
                binding.txtMainDescription.setGone()
                initRecyclerView(topic)
            }
        }
    }

    private fun initRecyclerView(topic: Topic) {
        binding.rvTopicFragment.apply {
            adapter = SubTopicAdapter(topic) { subTopic ->
                val intent = Intent(requireActivity(), subTopic.targetActivity)
                startActivity(intent)
            }
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    private fun getTopic(): Topic? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("topic", Topic::class.java)
        } else {
            arguments?.getParcelable("topic")
        }
}