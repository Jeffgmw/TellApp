package com.teller.tellapp.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DrawerValue
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberDrawerState
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
     TellAppTheme {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Drawer Icon",
                    modifier = Modifier
                        .clickable {
                            openDrawer() // Call the openDrawer callback to open the drawer
                        }
                        .padding(16.dp)
                )

//                ModalDrawerSample(navController = navController)

                // Bank Logo
                BankLogo()

                // Spacer to push DateAndTime to the right
                Spacer(modifier = Modifier.weight(1f))

                // Date and Time
                DateAndTime()

            }

            val context = LocalContext.current

            LogoutButton(
                logout = {
                    logout(context, navController)
                    // Implement logout functionality here
                    // For example, call logout function or clear user session
                },
                navController = navController
            )

            TopButtons(navController = navController)

            Spacer(modifier = Modifier.height(0.dp))

            TellerAndTellerGls(navController = navController)

            Spacer(modifier = Modifier.height(0.dp))

            SearchSection(navController = navController) {

            }
            Spacer(modifier = Modifier.height(5.dp))

            ButtonsTrans(navController = navController)

            Spacer(modifier = Modifier.height(16.dp))

            ScanButton(navController = navController)

        }

        // Bottom navigation

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
            .padding(start = 0.dp)
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
        modifier = Modifier.padding(10.dp)
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

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End, // Align horizontally to the end
        modifier = Modifier.padding(end = 10.dp, start = 320.dp) // Add padding to the end
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
            painter = painterResource(id = R.drawable.logout),
            contentDescription = "Logout Icon",
            modifier = Modifier
                .size(30.dp) // Set the size of the image
                .clickable {
                    logout()
                    scope.launch {
                        navController.navigate(Route.LoginScreen().name) // Replace with your login screen route
                    }
                }
        )
    }
}

// Example implementation of logout function
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
fun TopButtons(navController: NavController) {
    val buttonColors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)

    Row(
        modifier = Modifier
            .fillMaxWidth()
//            .padding(10.dp)
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { navController.navigate(Route.TicketsScreen().name) },
            colors = buttonColors,
            modifier = Modifier
                .width(120.dp) // Increase button width
        ) {
            Text(
                text = "Tickets",
                color = Color.Black // Set text color to black
            )
        }
        Button(
            onClick = { navController.navigate(Route.ReferralsScreen().name) },
            colors = buttonColors,
            modifier = Modifier
                .width(120.dp) // Increase button width
        ) {
            Text(
                text = "Referrals",
                color = Color.Black // Set text color to black
            )
        }

        Button(
            onClick = { navController.navigate(Route.TellerReportScreen().name) },
            colors = buttonColors,
            modifier = Modifier
                .width(120.dp) // Increase button width
        ) {
            Text(
                text = "Reports",
                color = Color.Black
            )
        }
    }
}

@Composable
fun TellerAndTellerGls(navController: NavController) {

    val textColor = if (isSystemInDarkTheme()) {
        Color.White // White color in dark mode
    } else {
        Color.Black
    }

    val boxBackgroundGradiant = Brush.verticalGradient(
        colors = listOf(
            colorResource(id = R.color.gray),
            colorResource(id = R.color.middle_red)
        )
    )
    Column {
        Box(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .padding(top = 12.dp, bottom = 24.dp)
                .border(1.dp, color = colorResource(id = R.color.darkergray))
                .fillMaxWidth() // Take full width
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .background(boxBackgroundGradiant)
                        .height(265.dp)
                        .width(10.dp)
                        .border(1.dp, color = colorResource(id = R.color.gray))
                )
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
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
                        Text(
                            text = "Active",
                            color = textColor,
                            fontSize = 16.sp,
                            modifier = Modifier
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
                                    val textColor = if (itemIndex == 0 || itemIndex == 1 || itemIndex == 3) textColor else Color.Black
                                    val cellColor = if (itemIndex == 2 && isAvailableBalGreaterThanRequired) Color.Green
                                    else if (itemIndex == 2 && !isAvailableBalGreaterThanRequired) Color.Red
                                    else textColor
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
fun ClickableTextItem(text: String) {

    val textColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = text,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .clickable { /* Handle click here */ }
                .padding(start = 3.dp)
        )
        Spacer(modifier = Modifier.width(18.dp))
    }
}

@Composable
fun SearchSection(navController: NavController, openDrawer: () -> Unit) {

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
            colorResource(id = R.color.gray),
            colorResource(id = R.color.middle_red)
        )
    )

    var expanded by remember { mutableStateOf(false) }
    val dropdownItems = listOf("Account", "Name", "Id/Passport", "Phone Number", "CIF")
    var selectedItem by remember { mutableStateOf(dropdownItems.first()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
//            .padding(horizontal = 13.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Search Account",
            color = textColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .padding(horizontal = 12.dp, vertical = 4.dp)

        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .padding(horizontal = 6.dp)
                .border(1.dp, color = colorResource(id = R.color.darkergray)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.round_search_24),
                contentDescription = null,
                colorFilter = ColorFilter.tint(colorResource(id = R.color.gray)),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(24.dp)
            )

            BasicTextField(
                value = searchTextState.value,
                onValueChange = { searchTextState.value = it },
                singleLine = true,
                textStyle = TextStyle(fontSize = 15.sp, color = textColor), // Use textColor for text color
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(start = 4.dp, end = 16.dp)
                    .weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                decorationBox = { innerTextField ->
                    innerTextField()
                    if (searchTextState.value.text.isEmpty()) {
                        Text(
                            text = "Search",
                            color = textColor, // Use textColor for text color
                            fontSize = 15.sp
                        )
                    }
                }
            )

            Box(
                modifier = Modifier
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
                        .border(1.dp, color = colorResource(id = R.color.black))
                        .padding(12.dp)
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
fun ButtonsTrans(navController: NavController) {
    val buttonColors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { navController.navigate(Route.TellerDetailsScreen().name) },
            colors = buttonColors,
            modifier = Modifier
                .width(120.dp) // Increase button width
        ) {
            Text(
                text = "Product1",
                color = Color.Black // Set text color to black
            )
        }

        Button(
            onClick = { navController.navigate(Route.TransPageScreen().name) },
            colors = buttonColors,
            modifier = Modifier
                .width(120.dp)
        ) {
            Text(
                text = "TransPage",
                color = Color.Black
            )
        }

        Button(
            onClick = { navController.navigate(Route.TransactionScreen().name) },
            colors = buttonColors,
            modifier = Modifier
                .width(130.dp)
        ) {
            Text(
                text = "Transactions",
                color = Color.Black
            )
        }
    }
}


@Composable
fun ModalDrawerSample(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(onCloseDrawer = { scope.launch { drawerState.close() } })
        },
        content = {
            Column {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Drawer Icon",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(Route.DrawerScreen().name)
                            scope.launch { drawerState.open() }
                        } // Clickable modifier to open the drawer
                        .padding(16.dp)
                )
            }
        }
    )
}

@Composable
fun DrawerContent(onCloseDrawer: () -> Unit) {
    Column {
        // Add your drawer content here
        Text("Drawer Content")
        Button(onClick = onCloseDrawer) {
            Text("Close Drawer")
        }
    }
}

@Composable
fun ScanButton(navController: NavController) {
    val buttonColors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)

    Box(
        modifier = Modifier
            .width(500.dp)
            .height(120.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                // Navigate to the QRCodeScreen when clicked
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

