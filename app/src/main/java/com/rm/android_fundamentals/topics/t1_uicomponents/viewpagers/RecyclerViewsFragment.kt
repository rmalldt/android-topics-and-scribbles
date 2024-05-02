package com.rm.android_fundamentals.topics.t1_uicomponents.viewpagers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat.OrientationMode
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rm.android_fundamentals.databinding.FragmentRecyclerViewsBinding

class RecyclerViewsFragment : Fragment() {

    private var _binding: FragmentRecyclerViewsBinding? = null
    private val binding get() = _binding!!
    private val rvAdapter by lazy { RvAdapter() }

    private var isGridLayout = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerViewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerViewLinear()

        binding.run {
            btnRecyclerViews.setOnClickListener {
                isGridLayout = if (isGridLayout) {
                    rvLinear.layoutManager = LinearLayoutManager(requireContext())
                    !isGridLayout
                } else {
                    rvLinear.layoutManager =GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
                    !isGridLayout
                }
            }
        }
    }

    private fun initRecyclerViewLinear() {
        binding.rvLinear.run {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        addItems()
    }

    private fun addItems() {
        rvAdapter.addItems(RvTestData.getStringData())
    }
}