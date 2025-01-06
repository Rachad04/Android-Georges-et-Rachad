package com.usj.budgettracking

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.usj.budgettracking.data.BudgetCategory
import com.usj.budgettracking.databinding.ItemBudgetBinding

class BudgetAdapter(
    private val onItemEdit: (BudgetCategory) -> Unit,
    private val onItemDelete: (BudgetCategory) -> Unit
) : RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {

    private val budgetList = mutableListOf<BudgetCategory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val binding = ItemBudgetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BudgetViewHolder(binding, onItemEdit, onItemDelete)
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        val budgetCategory = budgetList[position]
        holder.bind(budgetCategory)
    }

    override fun getItemCount(): Int = budgetList.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<BudgetCategory>) {
        budgetList.clear()
        budgetList.addAll(newList)
        notifyDataSetChanged() // Simple refresh; optimize later with DiffUtil if needed
    }

    class BudgetViewHolder(
        private val binding: ItemBudgetBinding,
        private val onItemEdit: (BudgetCategory) -> Unit,
        private val onItemDelete: (BudgetCategory) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(budgetCategory: BudgetCategory) {
            binding.tvCategoryName.text = budgetCategory.name
            binding.tvCategoryLimit.text = "Limit: ${budgetCategory.limit}"
            binding.tvCurrentSpending.text = "Spent: ${budgetCategory.currentSpending}"

            // Edit and Delete button handlers
            binding.btnEdit.setOnClickListener { onItemEdit(budgetCategory) }
            binding.btnDelete.setOnClickListener { onItemDelete(budgetCategory) }
        }
    }
}
