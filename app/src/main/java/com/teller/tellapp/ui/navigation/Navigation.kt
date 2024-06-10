package com.teller.tellapp.ui.navigation

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
import com.teller.tellapp.ui.Home.HomeScreen
import com.teller.tellapp.ui.Home.QrCodeScanner.QRCodeScanner
import com.teller.tellapp.ui.ScreensFromHome.ChangePasswordScreen
import com.teller.tellapp.ui.ScreensFromHome.CustomerDetailsScreen
import com.teller.tellapp.ui.ScreensFromHome.GLTransactionsPage
import com.teller.tellapp.ui.ScreensFromHome.ReferralsPage
import com.teller.tellapp.ui.ScreensFromHome.TellerDetails
import com.teller.tellapp.ui.login.ForgotPasswordPage
import com.teller.tellapp.ui.login.LoginScreen


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
                    onForgotPasswordClick = {
                        navHostController.navigate(Route.ForgotPasswordScreen().name)
                    },
                    navController = navHostController
                )
            }
            composable(route = Route.ForgotPasswordScreen().name) {
                ForgotPasswordPage(navController = navHostController)
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
