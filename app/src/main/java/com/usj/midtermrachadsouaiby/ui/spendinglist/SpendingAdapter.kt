package com.usj.midtermrachadsouaiby.ui.spendinglist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.usj.midtermrachadsouaiby.data.Spending
import com.usj.midtermrachadsouaiby.databinding.SpendingItemBinding

class SpendingAdapter : RecyclerView.Adapter<SpendingAdapter.SpendingViewHolder>() {

    private var spendings = emptyList<Spending>()

    class SpendingViewHolder(private val binding: SpendingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(spending: Spending) {
            binding.itemTitle.text = spending.description
            binding.itemAmount.text = spending.amount.toString()
            binding.itemDate.text = spending.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpendingViewHolder {
        // Inflate the layout using ViewBinding
        val binding = SpendingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpendingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpendingViewHolder, position: Int) {
        holder.bind(spendings[position])
    }

    override fun getItemCount() = spendings.size

    fun setSpendings(spendings: List<Spending>) {
        this.spendings = spendings
        notifyDataSetChanged()
    }
}
