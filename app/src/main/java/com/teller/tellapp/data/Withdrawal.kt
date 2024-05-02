package com.teller.tellapp.data

data class Withdraw(
     override val id: Long,
    // Common properties for all transactions
     override val transactionId: String,
     override val amount: Double,
     override val date: String,
     override val isCompleted: Boolean,
     override val imageData: String,
     override val tellerId: Long,
     override val accountId:Long,
     override val transactionType: TransactionType
    ): CustomerTransaction
