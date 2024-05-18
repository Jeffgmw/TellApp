package com.teller.tellapp.ui.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.teller.tellapp.R
import com.teller.tellapp.ui.theme.TellAppTheme
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(navController: NavController, openDrawer: () -> Unit)
{
    val keyboardController = LocalSoftwareKeyboardController.current

    TellAppTheme {

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { keyboardController?.hide() }
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    BankLogo()

                    // Spacer to push DateAndTime to the right
                    Spacer(modifier = Modifier.weight(1f))

                    val context = LocalContext.current

                    LogoutButton(
                        logout = {
                            logout(context, navController)

                        },
                        navController = navController
                    )

                }

                Spacer(modifier = Modifier.height(10.dp))

                DateAndTime()

                TellerAndTellerGls(navController = navController)

                Spacer(modifier = Modifier.height(0.dp))

                val accountNumberState = remember { mutableStateOf("") }

                SearchSection(
                    navController = navController,
                    openDrawer = { /* Implement openDrawer logic here */ },
                    onAccountNumberChange = { newValue ->
                        accountNumberState.value = newValue
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                TopButtons(navController = navController)

                Spacer(modifier = Modifier.height(20.dp))

                ActionButtons(navController = navController)

                Spacer(modifier = Modifier.height(20.dp))

                ScanButton(navController = navController)

            }
        }
    }
}



@Composable
fun BankLogo() {
    val logoResource = if (isSystemInDarkTheme()) {
        R.drawable.equityjpg2
    } else {
        R.drawable.equityb // Default image resource for light mode
    }

    Image(
        painter = painterResource(id = logoResource),
        contentDescription = "Bank Logo",
        modifier = Modifier
            .padding(start = 12.dp)
            .padding(0.dp)
            .size(70.dp)
    )
}

@Composable
fun DateAndTime() {
    val textColor = if (isSystemInDarkTheme()) {
        Color.White // White color in dark mode
    } else {
        Color.Black
    }

    val currentTime by rememberUpdatedState(getCurrentTime())

    Text(
        text = currentTime,
        color = textColor,
        fontSize = 14.sp,
        modifier = Modifier.padding(start = 15.dp, top = 10.dp)
    )
}

@Composable
fun getCurrentTime(): String {
    val currentTime = remember { mutableStateOf("") }

    LaunchedEffect(true) {
        while (true) {
            currentTime.value = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault()).format(
                Date()
            )
            delay(60000) // Update every minute
        }
    }

    return currentTime.value
}

