package com.rm.android_fundamentals.topics.t1_uicomponents.recylerviews.simple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rm.android_fundamentals.databinding.FragmentSimpleRvBinding

class SimpleRvFragment : Fragment() {

    private var _binding: FragmentSimpleRvBinding? = null
    private val binding get() = _binding!!

    private val rvAdapter: SimpleRvAdapter by lazy { SimpleRvAdapter { rowId -> onRowItemClicked(rowId) } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSimpleRvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        setAdapterData()
    }

    private fun initRecyclerView() {
        binding.rvSimple.run {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setAdapterData() {
        rvAdapter.dataList = SimpleRvItem.getSimpleRvItems()
    }

    private fun onRowItemClicked(rowId: String) {
        binding.tvClickedOnRow.text = rowId
    }
}
