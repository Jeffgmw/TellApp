package com.teller.tellapp.data

data class Deposit(
    override val id: Long,
    override val transactionId: String,
    override val amount: Double,
    override val date: String,
    override val isCompleted: Boolean,
    override val imageData: String,
    override val tellerId: Long,
    override val accountId:Long,
    override val transactionType: TransactionType,
    val depositer: String,
    val depositerId: String,
    val depositerNo: String
    ): CustomerTransaction
