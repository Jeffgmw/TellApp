package com.teller.tellapp.data

data class ReferralTrans(
    val admin_id: Long,
    val amount: Double,
    val completed: Boolean,
    val destAcc: Long,
    val date: String?,
    val id: Long,
    val referralId: String?,
    val referralType: String?,
    val sourceAcc: Long
)