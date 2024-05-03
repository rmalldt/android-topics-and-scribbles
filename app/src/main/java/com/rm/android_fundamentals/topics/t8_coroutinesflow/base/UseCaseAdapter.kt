package com.rm.android_fundamentals.topics.t8_coroutinesflow.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rm.android_fundamentals.databinding.RecyclerviewItemBinding

class UseCaseAdapter(
    private val useCaseCategory: UseCaseCategory,
    private val onUseCaseClick: (UseCase) -> Unit
) : RecyclerView.Adapter<UseCaseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtRvItem.apply {
            text = useCaseCategory.useCases[position].description

            setOnClickListener {
                onUseCaseClick(useCaseCategory.useCases[position])
            }
        }
    }

    override fun getItemCount() = useCaseCategory.useCases.size

    class ViewHolder(val binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root)
}