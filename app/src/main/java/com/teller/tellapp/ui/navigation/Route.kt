package com.teller.tellapp.ui.navigation

sealed class Route {
    data class LoginScreen(val name: String = "Login") : Route()
    data class ForgotPasswordScreen(val name: String = "Forgot Password") : Route()
    data class HomeScreen(val name: String = "Home") : Route()
    data class ReportsScreen(val name: String = "Reports") : Route()
    data class TransactionsScreen(val name: String = "Transactions") : Route()
    data class TicketsScreen(val name: String = "Tickets") : Route()
    data class ReferralsScreen(val name: String = "Referrals") : Route()
    data class QRCodeScreen(val name: String = "Scan") : Route()
    data class GLScreen (val name: String = "Row") : Route()
    data class EditScannedDataScreen(val name: String = "edit_scanned_data/{qrCode}") : Route()
    data class TellerDetailsScreen(val name: String = "TellerDetailsScreen") : Route()
    data class CustomerDetailsScreen(val name: String = "customerDetails/{searchQuery}") : Route()
    data class ChangePasswordScreen(val name: String = "ChangePasswordScreen") : Route()
}