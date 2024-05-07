package com.rm.android_fundamentals.topics.t8_coroutinesflow.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rm.android_fundamentals.databinding.RecyclerviewItemBinding

class UseCaseCategoryAdapter(
    private val useCaseCategories: List<UseCaseCategory>,
    private val onUseCaseCategoryClick: (UseCaseCategory) -> Unit
) : RecyclerView.Adapter<UseCaseCategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtRv.apply {
            text = useCaseCategories[position].categoryName

            setOnClickListener {
                onUseCaseCategoryClick(useCaseCategories[position])
            }
        }
    }

    override fun getItemCount() = useCaseCategories.size

    class ViewHolder(val binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root)
}