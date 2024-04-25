package com.teller.tellapp.data

data class Deposit(
    override val id: Long,
    override val transactionId: String,
    override val amount: Double,
    override val date: String,
    override val isCompleted: Boolean,
    override val imageData: String,
    val depositer: String,
    val depositerId: String,
    val depositerNo: String,

    ) : Transaction()
