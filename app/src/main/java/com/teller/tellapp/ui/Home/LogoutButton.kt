package com.teller.tellapp.ui.Home

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.teller.tellapp.R
import com.teller.tellapp.ui.navigation.Route
import kotlinx.coroutines.launch

@Composable
fun LogoutButton(logout: () -> Unit, navController: NavController) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val textColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopEnd
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.padding(end = 16.dp, start = 16.dp)
        ) {
            Text(
                text = "Teller 1",
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.padding(end = 10.dp)
            )

            Box {
                Image(
                    painter = painterResource(id = R.drawable.logoutic),
                    contentDescription = "Logout Icon",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { expanded = true }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(onClick = {
                        expanded = false
                        logout()
                        scope.launch {
                            navController.navigate(Route.LoginScreen().name)
                        }
                    }) {
                        Text("Logout")
                    }
                    DropdownMenuItem(onClick = {
                        expanded = false
                        navController.navigate("ChangePasswordScreen")
                    }) {
                        Text("Change Password")
                    }
                }
            }
        }
    }
}

fun logout(context: Context, navController: NavController) {
    val sharedPreferences = context.getSharedPreferences("my_app_pref", Context.MODE_PRIVATE)
    sharedPreferences.edit().clear().apply()
    // Clear the back stack and navigate to the login screen with a new task
    navController.navigate(Route.LoginScreen().name) {
        popUpTo(navController.graph.startDestinationId) {
            // Inclusive will clear the start destination as well
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

