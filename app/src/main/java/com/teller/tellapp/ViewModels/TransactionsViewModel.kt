package com.teller.tellapp.ViewModels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teller.tellapp.data.Transaction
import com.teller.tellapp.network.RetrofitClient
import kotlinx.coroutines.launch

class TransactionViewModel : ViewModel() {

    private val apiService = RetrofitClient.instance

    var depositTransactions = mutableStateListOf<Transaction>()
        private set

    var withdrawalTransactions = mutableStateListOf<Transaction>()
        private set

    var isLoading = mutableStateOf(true)
        private set

    init {
        fetchTransactions()
    }

    private fun fetchTransactions() {
        viewModelScope.launch {
            try {
                val response = apiService.getAllTransactions()

                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        val transactions = responseBody.data ?: emptyList()

                        transactions.forEach { transaction ->
                            val updatedTransaction = transaction.copy(
                                customer = transaction.customer,
                                customerAccount = transaction.customerAccount
                            )
                            if (transaction.transactionType == "Deposit") {
                                depositTransactions.add(updatedTransaction)
                            } else if (transaction.transactionType == "Withdrawal") {
                                withdrawalTransactions.add(updatedTransaction)
                            }
                        }
                    } else {
                        // Handle empty response body
                        Log.e("TransactionViewModel", "Empty response body")
                    }
                } else {
                    // Handle unsuccessful response
                    Log.e("TransactionViewModel", "Failed to fetch transactions: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }
    }
}
