package com.teller.tellapp

import TellerReportsPage
import TicketsPage
import TransactionsPage
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teller.tellapp.ui.GLTransactionsPage
import com.teller.tellapp.ui.NavDrawer
import com.teller.tellapp.ui.QRCodeScanner
import com.teller.tellapp.ui.ReferralsPage
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
    data class TellerReportScreen(val name: String = "Reports") : Route()
    data class TransactionScreen(val name: String = "Transactions") : Route()
    data class TicketsScreen(val name: String = "Tickets") : Route()
    data class ReferralsScreen(val name: String = "Referrals") : Route()
    data class DrawerScreen(val name: String = "Drawer") : Route()
    data class FinacleScreen(val name: String = "Product") : Route()
    data class TransPageScreen(val name: String = "TransaPage") : Route()
    data class QRCodeScreen(val name: String = "Scan") : Route()
    data class GLScreen (val name: String = "Row") : Route()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyNavigation(navHostController: NavHostController) {

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
//        composable(route = Route.DrawerScreen().name){
//            NavDrawer(navHostController = navHostController) {
//
//            }
//        }

        composable(route = Route.TellerReportScreen().name) {
            TellerReportsPage()

        }
        composable(route = Route.TransactionScreen().name){
            TransactionsPage()

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
        composable(route = Route.QRCodeScreen().name){
            QRCodeScanner(navController = navHostController)
        }

        composable(Route.GLScreen().name){
            GLTransactionsPage()
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