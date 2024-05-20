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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.teller.tellapp.ui.SrcHomeScreen.ChangePasswordScreen
import com.teller.tellapp.ui.SrcHomeScreen.CustomerDetailsScreen
import com.teller.tellapp.ui.SrcHomeScreen.GLTransactionsPage
import com.teller.tellapp.ui.Home.HomeScreen
import com.teller.tellapp.ui.Home.QrCode.QRCodeScanner
import com.teller.tellapp.ui.SrcHomeScreen.ReferralsPage
import com.teller.tellapp.ui.SrcHomeScreen.TellerDetails
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
    data class QRCodeScreen(val name: String = "Scan") : Route()
    data class GLScreen (val name: String = "Row") : Route()
    data class EditScannedDataScreen(val name: String = "edit_scanned_data/{qrCode}") : Route()
    data class TellerDetailsScreen(val name: String = "TellerDetailsScreen") : Route()
    data class CustomerDetailsScreen(val name: String = "customerDetails/{searchQuery}") : Route()
    data class ChangePasswordScreen(val name: String = "ChangePasswordScreen") : Route()
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
                        navHostController.navigate(Route.ForgotPasswordScreen().name)
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
            HomeScreen(navController = navController) {
                
            }
        }

        composable(route = Route.ReportsScreen().name) {
            ReportsPage(navController = navController)
        }

        composable(route = Route.TransactionsScreen().name) {
            TransactionsPage(navController = navController)
        }

        composable(route = Route.TicketsScreen().name) {
            TicketsPage()
        }

        composable(route = Route.ReferralsScreen().name) {
            ReferralsPage()
        }

        composable(Route.QRCodeScreen().name) {
            QRCodeScanner(
                navController = navHostController,
                onQrCodeSubmit = { data ->
                    navController.navigate(Route.EditScannedDataScreen(data).name)
                },
                onCancel = {

                }
            )
        }

        composable(Route.GLScreen().name) {
            GLTransactionsPage()
        }

        composable(Route.EditScannedDataScreen().name) { backStackEntry ->
            // Retrieve the qrCode argument from the backStackEntry
            val qrCode = backStackEntry.arguments?.getString("qrCode") ?: ""

            Log.d("EditScannedDataScreen", "Received QR code: $qrCode")

            EditScannedDataScreen(navController, qrCode)
        }

        composable(Route.TellerDetailsScreen().name) {
            TellerDetails(navController = navController)
        }

        composable(
            route = Route.CustomerDetailsScreen().name,
            arguments = listOf(navArgument("searchQuery") { type = NavType.StringType })
        ) { backStackEntry ->
            val searchQuery = backStackEntry.arguments?.getString("searchQuery") ?: ""
            CustomerDetailsScreen(navController, searchQuery)
        }

        composable(Route.ChangePasswordScreen().name){
            ChangePasswordScreen(navController = navController)
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
