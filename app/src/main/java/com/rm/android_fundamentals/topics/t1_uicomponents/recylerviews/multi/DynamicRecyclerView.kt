package com.rm.android_fundamentals.topics.t1_uicomponents.recylerviews.multi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.databinding.FragmentRecyclerViewsBinding

class DynamicRecyclerView : Fragment() {

    private var _binding: FragmentRecyclerViewsBinding? = null
    private val binding get() = _binding!!
    private val multipleViewHoldersAdapterMultiViewModelsAdapter by lazy { MultipleViewHoldersAdapter() }

    private lateinit var rvAdjustableGridAdapter: AdjustableLayoutAdapter

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

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(getRvAdjustGridMenuProvider(), viewLifecycleOwner, Lifecycle.State.RESUMED)

        initRvWithMultipleViewHolders()
    }

    // RecyclerView with multiple types of ViewHolders
    private fun initRvWithMultipleViewHolders() {
        binding.rvLinear.run {
            adapter = multipleViewHoldersAdapterMultiViewModelsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        multipleViewHoldersAdapterMultiViewModelsAdapter.addItems(forMultipleViewHolders())
    }


    private fun getRvAdjustGridMenuProvider() = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.layout_types_menu, menu)
            }

            // Single ViewHolder with two different layouts as per LayoutManager type
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.layoutTypesMenuItem) {
                    isGridLayout = if (isGridLayout) {
                        // If Linearlayout manager, load ViewHolder for linear items
                        rvAdjustableGridAdapter = AdjustableLayoutAdapter("linear")
                        binding.rvLinear.adapter = rvAdjustableGridAdapter
                        binding.rvLinear.layoutManager = LinearLayoutManager(requireContext())
                        rvAdjustableGridAdapter.addItems(forLinearViewHolders())
                        !isGridLayout
                    } else {
                        // If Gridlayout manager, load ViewHolder for grid items
                        rvAdjustableGridAdapter = AdjustableLayoutAdapter("grid")
                        binding.rvLinear.adapter = rvAdjustableGridAdapter
                        binding.rvLinear.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
                        rvAdjustableGridAdapter.addItems(forLinearViewHolders())
                        !isGridLayout
                    }
                }
                return false
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}