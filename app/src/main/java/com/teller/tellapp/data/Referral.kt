package com.teller.tellapp.data

data class Referral(
    val admin_id: Long,
    val amount: Double,
    val completed: Boolean,
    val destAcc: Long,
    val id: Long,
    val referralId: String,
    val referralType: String,
    val sourceAcc: Long
)