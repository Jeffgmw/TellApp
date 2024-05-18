package com.teller.tellapp.data

data class Customer(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val nationalId: String,
    val phoneNumber: String,
    val customerAccount: List<CustomerAccount>
)

data class CustomerAccount(
    val id: Long,
    val accno: Long,
    val balance: Double,
    val customerId: Long,
    val transaction: List<Transaction>
)

data class Transaction(
    val id: Long,
    val transactionId: String,
    val amount: Double,
    val date: String,
    val imageData: String,
    val accountId: Long,
    val transactionType: String,
    val depositer: String?,
    val depositerNo: String?,
    val depositerId: String?,
    val completed: Boolean
)
