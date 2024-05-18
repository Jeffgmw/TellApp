package com.teller.tellapp.data

data class ChangePassword(
    val confirmPassword: String,
    val emailAddress: String,
    val oldPassword: String,
    val password: String
)