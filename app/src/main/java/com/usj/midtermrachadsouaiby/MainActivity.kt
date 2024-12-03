package com.usj.midtermrachadsouaiby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState // Correct import for LiveData extension
import androidx.compose.ui.Modifier

import com.usj.midtermrachadsouaiby.data.Spending
import com.usj.midtermrachadsouaiby.ui.theme.MidtermRachadSouaibyTheme
import com.usj.midtermrachadsouaiby.viewmodel.SpendingViewModel

class MainActivity : ComponentActivity() {
    // Access the SpendingViewModel
    private val spendingViewModel: SpendingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Correct place to call setContent (inside onCreate)
        setContent {
            MidtermRachadSouaibyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SpendingListScreen(spendingViewModel) // Correct usage of the ViewModel
                }
            }
        }
    }
}

@Composable
fun SpendingListScreen(viewModel: SpendingViewModel) {
    // Observe the allSpendings LiveData from the ViewModel and convert it to a state.
    val spendingList = viewModel.allSpendings.observeAsState(emptyList()) // Correct use of observeAsState

    // LazyColumn to display the list of spendings
    LazyColumn {
        items(spendingList.value) { spending -> // Correctly using spendingList
            SpendingItem(spending = spending) // Passing the Spending object to SpendingItem
        }
    }
}

@Composable
fun SpendingItem(spending: Spending) { // Change type to Spending
    // Correct usage of properties from Spending class
    Text(text = "${spending.description}: \$${spending.amount} on ${spending.date}")
}
