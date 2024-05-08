package com.teller.tellapp

import EditScannedDataScreen
import ReportsPage
import TicketsPage
import TransactionsPage
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teller.tellapp.network.RetrofitClient
import com.teller.tellapp.ui.GLTransactionsPage
import com.teller.tellapp.ui.NavDrawer
import com.teller.tellapp.ui.QRCodeScanner
import com.teller.tellapp.ui.ReferralsPage
import com.teller.tellapp.ui.TellerDetails
import com.teller.tellapp.ui.WithdrawalPage
import com.teller.tellapp.ui.login.ForgotPasswordPage
import com.teller.tellapp.ui.login.LoginScreen
import com.teller.tellapp.ui.signup.PolicyScreen
import com.teller.tellapp.ui.signup.PricacyScreen
import com.teller.tellapp.ui.signup.SignUpScreen


sealed class Route {
    data class LoginScreen(val name: String = "Login") : Route()
    data class ForgotPasswordScreen(val name: String = "Forgot Password") : Route()
    data class SignUpScreen(val name: String = "SignUp") : Route()
    data class PrivacyScreen(val name: String = "Privacy") : Route()
    data class PolicyScreen(val name: String = "Policy") : Route()
    data class HomeScreen(val name: String = "Home") : Route()
    data class ReportsScreen(val name: String = "Reports") : Route()
    data class TransactionsScreen(val name: String = "Transactions") : Route()
    data class TicketsScreen(val name: String = "Tickets") : Route()
    data class ReferralsScreen(val name: String = "Referrals") : Route()
    data class TransPageScreen(val name: String = "TransaPage") : Route()
    data class QRCodeScreen(val name: String = "Scan") : Route()
    data class GLScreen (val name: String = "Row") : Route()
    data class EditScannedDataScreen(val name: String = "edit_scanned_data/{qrCode}") : Route()
    data class TellerDetailsScreen(val name: String = "Details") : Route()
}

@Composable
fun MyNavigation(navHostController: NavHostController, navController: NavController) {


    NavHost(
        navController = navHostController,
        startDestination = "login_flow",
    ) {
        navigation(startDestination = Route.LoginScreen().name, route = "login_flow") {
            composable(route = Route.LoginScreen().name) {
                LoginScreen(
                    onLoginClick = {
                        navHostController.navigate(
                            Route.HomeScreen().name
                        ) {
                            popUpTo(route = "login_flow")
                        }
                    },
                    onSignUpClick = {
                        navHostController.navigateToSingleTop(
                            Route.SignUpScreen().name
                        )
                    },
                    onForgotPasswordClick = {

                        navHostController.navigate(Route.ForgotPasswordScreen().name) // Navigate to Forgot Password screen
                    },
                    navController = navHostController

                )
            }
            composable(route = Route.ForgotPasswordScreen().name) {
                ForgotPasswordPage(navController = navHostController)
            }

            composable(route = Route.SignUpScreen().name) {
                SignUpScreen(
                    onSignUpClick = {
                        navHostController.navigate(
                            Route.HomeScreen().name
                        ) {
                            popUpTo("login_flow")
                        }
                    },
                    onLoginClick = {
                        navHostController.navigateToSingleTop(
                            Route.LoginScreen().name
                        )
                    },
                    onPrivacyClick = {
                        navHostController.navigate(
                            Route.PrivacyScreen().name
                        ) {
                            launchSingleTop = true
                        }
                    },
                    onPolicyClick = {
                        navHostController.navigate(
                            Route.PolicyScreen().name
                        ) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(route = Route.PolicyScreen().name) {
                PolicyScreen {
                    navHostController.navigateUp()
                }
            }
            composable(route = Route.PrivacyScreen().name) {
                PricacyScreen {
                    navHostController.navigateUp()

                }
            }
        }

        composable(route = Route.HomeScreen().name) {
            NavDrawer(navHostController = navHostController) {
            }

        }

        composable(route = Route.ReportsScreen().name) {
            ReportsPage(navController= navController)

        }
        composable(route = Route.TransactionsScreen().name){
            TransactionsPage(navController= navController)


        }
        composable(route = Route.TicketsScreen().name){
           TicketsPage()
        }
        composable(route= Route.ReferralsScreen().name){
            ReferralsPage()
        }

        composable(Route.TransPageScreen().name) {
            WithdrawalPage(
                navController = navHostController,
                onSubmit = { formData ->
                },
                onCancel = {
                }
            )
        }

        composable(Route.QRCodeScreen().name) {

            QRCodeScanner(
                navController = navHostController,
                onQrCodeSubmit = { data ->

                    Log.d("QRCodeScanner", "QR code scanned: $data")
                    // Navigate to the editing screen with the scanned data
                    Log.d("QRCodeScanner", "Navigating to EditScannedDataScreen with QR code: $data")
                    navController.navigate(Route.EditScannedDataScreen(data).name)

                },
                onCancel = {
                    // Handle cancel logic here
                }
            )
        }

        composable(Route.GLScreen().name){
            GLTransactionsPage()
        }

        composable(Route.EditScannedDataScreen().name) { backStackEntry ->
            // Retrieve the qrCode argument from the backStackEntry
            val qrCode = backStackEntry.arguments?.getString("qrCode") ?: ""
            val apiService = RetrofitClient.instance // Get Retrofit service instance

            Log.d("EditScannedDataScreen", "Received QR code: $qrCode")

            EditScannedDataScreen(navController, qrCode)

        }

        composable(Route.TellerDetailsScreen().name){
           TellerDetails(navController = navController)
        }

    }
}


fun NavController.navigateToSingleTop(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}