package com.teller.tellapp.data

sealed class CustomerTransaction {
    abstract val id: Long
    // Common properties for all transactions
    abstract val transactionId: String
    abstract val amount: Double
    abstract val date: String
    abstract val isCompleted: Boolean
    abstract val imageData: String

}