package com.teller.tellapp.ViewModels
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teller.tellapp.data.Transaction
import com.teller.tellapp.network.RetrofitClient
import kotlinx.coroutines.launch


class TransactionsViewModel : ViewModel() {
    private val _transactions = MutableLiveData<List<Transaction>>()
    val transactions: LiveData<List<Transaction>> get() = _transactions

    init {
        fetchTransactions()
    }

    private fun fetchTransactions() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getAllTransactions()
                if (response.statusCode == 200) {
                    _transactions.value = response.entity ?: emptyList()
                } else {
                    // Handle error case
                    Log.e("TransactionsViewModel", "Error: ${response.message}")
                }
            } catch (e: Exception) {
                // Handle exception
                Log.e("TransactionsViewModel", "Exception: ${e.message}")
            }
        }
    }
}
