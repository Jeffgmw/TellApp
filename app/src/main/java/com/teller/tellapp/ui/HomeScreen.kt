package com.teller.tellapp.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.LocalContentColor
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.teller.tellapp.R
import com.teller.tellapp.Route
import com.teller.tellapp.ui.theme.TellAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
                             // Implement logout functionality
                         },
                         navController = navController
                     )

                 }

                 Spacer(modifier = Modifier.height(10.dp))

                 DateAndTime()

                 TellerAndTellerGls(navController = navController)

                 Spacer(modifier = Modifier.height(0.dp))

                 SearchSection(navController = navController) {

                 }

                 Spacer(modifier = Modifier.height(10.dp))

                 TopButtons(navController = navController)

                 Spacer(modifier = Modifier.height(40.dp))

                 ScanButton(navController = navController)

             }
         }
     }
}

@Composable
fun BankLogo() {
    val logoResource = if (isSystemInDarkTheme()) {
        R.drawable.equityjpg2 // Change this to the appropriate dark mode image resource
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
        Color.Black // Black color in light mode
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

@Composable
fun LogoutButton(logout: () -> Unit, navController: NavController) {
    val scope = rememberCoroutineScope()
    val textColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopEnd // Aligns the content to the top end of the Box
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.padding(end = 16.dp, start = 16.dp)
        ) {
            // Logout Text
            Text(
                text = "Logout",
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.padding(end = 10.dp)
            )

            // Logout Icon
            Image(
                painter = painterResource(id = R.drawable.logoutic),
                contentDescription = "Logout Icon",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        logout()
                        scope.launch {
                            navController.navigate(Route.LoginScreen().name)
                        }
                    }
            )
        }
    }
}


// implementation of logout function
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




@Composable
fun TellerAndTellerGls(navController: NavController) {

    val backgroundColor = if (isSystemInDarkTheme()) {
        colorResource(id = R.color.grayEq) // Dark theme background color
    } else {
        colorResource(id = R.color.grayEq) // Light theme background color
    }


    val textColor = if (isSystemInDarkTheme()) {
        Color.Black // White color in dark mode
    } else {
        Color.Black
    }

    Column {
        Box(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .padding(top = 10.dp, bottom = 24.dp)
                .fillMaxWidth()
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(13.dp) // Add shape here
                )
        ) {
            Row {

                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { navController.navigate(Route.TellerDetailsScreen().name) }) {
                        Image(
                            painter = painterResource(id = R.drawable.darkidentity_24),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "Teller 1",
                            color = textColor,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    // Data Rows
                    val (clickedRowIndex, setClickedRowIndex) = remember { mutableStateOf(-1) }

                    Column {
                        // Table Header
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            val headers = listOf("Currency", "GL Account", "Available bal", "Required bal")
                            headers.forEach { header ->
                                Text(
                                    text = header,
                                    color = textColor,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f) // Expand to fill available space
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // Data Rows
                        val dataRows = listOf(
                            listOf("KES", "1409876536", "10,000,000.00", "9,000,000.00"),
                            listOf("USD", "1234567890", "8,000.00", "9,000.00"),
                            listOf("EUR", "9876543210", "3,000.00", "10,000.00"),
                            listOf("GBP", "0987654321", "2,000.00", "1,000.00")
                        )

                        dataRows.forEachIndexed { index, rowData ->
                            val availableBal = rowData[2].replace(",", "").toFloatOrNull() ?: 0f
                            val requiredBal = rowData[3].replace(",", "").toFloatOrNull() ?: 0f
                            val isAvailableBalGreaterThanRequired = availableBal > requiredBal
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clickable {
                                        when (index) {
                                            0 -> navController.navigate(Route.GLScreen().name)
                                            1 -> navController.navigate(Route.GLScreen().name) // Add navigation for index 1
                                            else -> setClickedRowIndex(index)
                                        }
                                    }
                            ) {
                                rowData.forEachIndexed { itemIndex, item ->
                                    val textColor = if (itemIndex == 0 || itemIndex == 1 || itemIndex == 3) {
                                        colorResource(id = R.color.black)
                                    } else {
                                        Color.Black // Default text color
                                    }

                                    val cellColor = if (itemIndex == 2 && isAvailableBalGreaterThanRequired) {
                                        colorResource(id = R.color.darkergreen)
                                    } else if (itemIndex == 2 && !isAvailableBalGreaterThanRequired) {
                                        colorResource(id = R.color.darkerred)
                                    } else {
                                        textColor
                                    }

                                    Text(
                                        text = item,
                                        color = cellColor,
                                        fontSize = 12.sp,
                                        modifier = Modifier.weight(1f) // Expand to fill available space
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SearchSection(navController: NavController, openDrawer: () -> Unit) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val textColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }
    val textColor2 = LocalContentColor.current

    val buttonColors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)

    val searchTextState = remember { mutableStateOf(TextFieldValue()) }
    val iconBackgroundGradient = Brush.verticalGradient(
        colors = listOf(
            colorResource(id = R.color.lightgray),
            colorResource(id = R.color.grayEq)
        )
    )

    var expanded by remember { mutableStateOf(false) }
    val dropdownItems = listOf("Account", "Name", "Id/Passport", "Phone Number", "CIF")
    var selectedItem by remember { mutableStateOf(dropdownItems.first()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 13.dp, vertical = 2.dp)
    ) {
        Text(
            text = "Search Account",
            color = textColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp, start = 10.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchTextState.value,
                onValueChange = { searchTextState.value = it },
                singleLine = true,
                textStyle = TextStyle(fontSize = 13.sp, color = textColor),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                label = {
                    Text(
                        text = selectedItem, // Use selectedItem as label text
                        color = textColor,
                        fontSize = 14.sp
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                trailingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.search24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.darkergray)),
                        modifier = Modifier.size(24.dp)
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = Color.DarkGray,
                    focusedBorderColor = colorResource(id = R.color.grayEq),
                    unfocusedBorderColor = colorResource(id = R.color.grayEq)
                )
            )

            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable {
                        expanded = true
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.round_filter_list_24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.black)),
                    modifier = Modifier
                        .size(48.dp)
                        .background(iconBackgroundGradient)
                        .padding(6.dp)
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.width(140.dp)
                ) {
                    dropdownItems.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                selectedItem = item
                                expanded = false
                            }
                        ) {
                            Text(
                                text = item,
                                color = textColor2,
                                modifier = Modifier.padding(vertical = 0.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopButtons(navController: NavController) {

    val buttonColors = ButtonDefaults.buttonColors(
        backgroundColor = colorResource(id = R.color.grayEq)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { navController.navigate(Route.TicketsScreen().name) },
            colors = buttonColors,
            modifier = Modifier
                .width(120.dp)
        ) {
            Text(
                text = "Tickets",
                color = Color.Black
            )
        }
        Button(
            onClick = { navController.navigate(Route.ReferralsScreen().name) },
            colors = buttonColors,
            modifier = Modifier
                .width(120.dp)
        ) {
            Text(
                text = "Referrals",
                color = Color.Black
            )
        }

        Button(
            onClick = { navController.navigate(Route.ReportsScreen().name) },
            colors = buttonColors,
            modifier = Modifier
                .width(120.dp)
        ) {
            Text(
                text = "Reports",
                color = Color.Black
            )
        }
    }
}



@Composable
fun ScanButton(navController: NavController) {

    val buttonColors = ButtonDefaults.buttonColors(
        backgroundColor = colorResource(id = R.color.grayEq)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                navController.navigate(Route.QRCodeScreen().name)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            colors = buttonColors

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.scan),
                        contentDescription = "Button Icon"
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Scan",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

