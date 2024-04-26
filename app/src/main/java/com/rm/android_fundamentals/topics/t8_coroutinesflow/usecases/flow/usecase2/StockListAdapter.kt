package com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.flow.usecase2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rm.android_fundamentals.base.TopicAdapter
import com.rm.android_fundamentals.databinding.RecyclerviewStockItemBinding
import com.rm.android_fundamentals.topics.t8_coroutinesflow.usecases.flow.mock.Stock
import java.text.NumberFormat

class StockListAdapter : RecyclerView.Adapter<StockListAdapter.ViewHolder>() {

    var stockList: List<Stock>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerviewStockItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stock = stockList?.get(position)
        with(holder.binding) {
            tvRank.text = stock?.rank.toString()
            tvName.text = stock?.name
            val currentPriceFormatted: String = currencyFormatter.format(stock?.currentPrice)
            tvCurrentPrice.text = currentPriceFormatted
        }
    }

    override fun getItemCount(): Int = stockList?.size ?: 0

    class ViewHolder(val binding: RecyclerviewStockItemBinding) : RecyclerView.ViewHolder(binding.root)
}