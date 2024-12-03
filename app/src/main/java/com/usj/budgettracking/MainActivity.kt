package com.usj.budgettracking

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.usj.budgettracking.data.BudgetCategory
import com.usj.budgettracking.databinding.ActivityMainBinding
import com.usj.budgettracking.databinding.DialogAddCategoryBinding

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var budgetAdapter: BudgetAdapter

    private val budgetViewModel: BudgetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView with edit and delete callbacks
        budgetAdapter = BudgetAdapter(
            onItemEdit = { budgetCategory -> showEditCategoryDialog(budgetCategory) },
            onItemDelete = { budgetCategory -> budgetViewModel.delete(budgetCategory) }
        )
        binding.budgetRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = budgetAdapter
        }

        // Handle "Add Category" button click
        binding.btnAddCategory.setOnClickListener {
            showAddCategoryDialog()
        }

        // Create notification channel
        createNotificationChannel()

        // Observe budget data changes
        budgetViewModel.allBudgets.observe(this) { budgets ->
            Log.d("BudgetTracking", "Observed budgets: ${budgets.size} categories.")
            budgetAdapter.submitList(budgets)
            checkBudgetAlerts(budgets)
        }
    }

    private fun showAddCategoryDialog() {
        val dialogBinding = DialogAddCategoryBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setTitle("Add Category")
            .setPositiveButton("Add") { _, _ ->
                val name = dialogBinding.categoryName.text.toString()
                val limit = dialogBinding.categoryLimit.text.toString().toDoubleOrNull() ?: 0.0

                if (name.isNotBlank() && limit > 0) {
                    val newCategory = BudgetCategory(name = name, limit = limit, currentSpending = 0.0)
                    Log.d("BudgetTracking", "Inserting new category: $name with limit $limit")
                    budgetViewModel.insert(newCategory)
                } else {
                    showErrorDialog("Please enter a valid name and limit.")
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun showEditCategoryDialog(category: BudgetCategory) {
        val dialogBinding = DialogAddCategoryBinding.inflate(layoutInflater)
        dialogBinding.categoryName.setText(category.name)
        dialogBinding.categoryLimit.setText(category.limit.toString())

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setTitle("Edit Category")
            .setPositiveButton("Update") { _, _ ->
                val updatedName = dialogBinding.categoryName.text.toString()
                val updatedLimit = dialogBinding.categoryLimit.text.toString().toDoubleOrNull() ?: 0.0

                if (updatedName.isNotBlank() && updatedLimit > 0) {
                    val updatedCategory = category.copy(name = updatedName, limit = updatedLimit)
                    budgetViewModel.update(updatedCategory)
                } else {
                    showErrorDialog("Please enter a valid name and limit.")
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "BudgetChannel"
            val channelName = "Budget Alerts"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = "Channel for budget limit alerts"
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun checkBudgetAlerts(budgets: List<BudgetCategory>) {
        budgets.forEach { category ->
            if (category.currentSpending >= category.limit * 0.8) {
                Log.d("BudgetTracking", "Sending alert for ${category.name}, current spending: ${category.currentSpending}")
                sendNotification(category)
            }
        }
    }

    private fun sendNotification(category: BudgetCategory) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this, "BudgetChannel")
            .setContentTitle("Budget Alert")
            .setContentText("${category.name} is nearing its budget limit!")
            .setSmallIcon(R.drawable.ic_warning) // Replace with your actual icon
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(category.id.toInt(), notification)
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}
