package com.rm.android_fundamentals.topics.t1_uicomponents.recylerviews.simple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rm.android_fundamentals.databinding.FragmentSimpleRvBinding
import com.rm.android_fundamentals.utils.toast

class SimpleRvFragment : Fragment() {

    private var _binding: FragmentSimpleRvBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvAdapter: SimpleRvAdapter

    private var dataList: ArrayList<SimpleRvItem> = arrayListOf()

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
        dataList = SimpleRvItem.getSimpleRvItems()
        rvAdapter = SimpleRvAdapter { rowId -> onRowItemClicked(rowId) }
        binding.rvSimple.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvAdapter
        }

        rvAdapter.dataList = dataList

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                dataList.removeAt(pos)
                rvAdapter.notifyItemRemoved(pos)
                requireContext().toast("Item at $pos removed")
            }
        }).attachToRecyclerView(binding.rvSimple)

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                dataList.removeAt(pos)
                rvAdapter.notifyItemRemoved(pos)
                requireContext().toast("Item at $pos archived")
            }
        }).attachToRecyclerView(binding.rvSimple)
    }

    private fun setAdapterData() {

    }

    private fun onRowItemClicked(rowId: String) {
        binding.tvClickedOnRow.text = rowId
    }
}
