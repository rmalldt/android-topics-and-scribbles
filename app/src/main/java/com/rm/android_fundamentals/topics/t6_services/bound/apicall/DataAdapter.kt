package com.rm.android_fundamentals.topics.t6_services.bound.apicall

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rm.android_fundamentals.databinding.RecyclerviewStockItemBinding
import com.rm.android_fundamentals.mocknetwork.mockflow.Stock
import com.rm.android_fundamentals.topics.t9_coroutinesflow.usecases.flow.usecase2.StockListAdapter
import java.text.NumberFormat

class DataAdapter : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    var stockList: List<Stock>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = RecyclerviewStockItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val stock = stockList?.get(position)
        with(holder.binding) {
            tvRank.text = stock?.rank.toString()
            tvName.text = stock?.name
            val currentPriceFormatted: String = currencyFormatter.format(stock?.currentPrice)
            tvCurrentPrice.text = currentPriceFormatted
        }
    }

    override fun getItemCount(): Int = stockList?.size ?: 0

    class DataViewHolder(val binding: RecyclerviewStockItemBinding) : RecyclerView.ViewHolder(binding.root)
}