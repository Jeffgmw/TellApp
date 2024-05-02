package com.teller.tellapp.data

interface CustomerTransaction {
    val id: Long
    val transactionId: String
    val amount: Double
    val date: String
    val isCompleted: Boolean
    val imageData: String
    val tellerId: Long
    val accountId: Long
    val transactionType: TransactionType // Using enum or constant
}


