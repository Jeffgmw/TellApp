package com.teller.tellapp.ui.Home


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.LocalContentColor
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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

@Composable
fun SearchSection(navController: NavController, openDrawer: () -> Unit, onAccountNumberChange: (String) -> Unit) {

    val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val textColor2 = LocalContentColor.current

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

    var errorMessage by remember { mutableStateOf<String?>(null) }

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

        // Show error message if there's any
        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 8.dp, start = 10.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchTextState.value,
                onValueChange = {
                    searchTextState.value = it
                    errorMessage = null  // Clear error message when input changes
                },
                singleLine = true,
                textStyle = TextStyle(fontSize = 13.sp, color = textColor),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                label = {
                    Text(
                        text = selectedItem,
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
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                val searchQuery = searchTextState.value.text
                                when (selectedItem) {
                                    "Account" -> {
                                        try {
                                            // Attempt to parse input as Long for Account search
                                            searchQuery.toLong()
                                            navController.navigate("customerDetails/$searchQuery")
                                        } catch (e: NumberFormatException) {
                                            // Handle invalid Long input by setting error message
                                            errorMessage = "Invalid account number"
                                        }
                                    }
                                    else -> {
                                        // Navigate with string query for other search types
                                        navController.navigate("customerDetails/$searchQuery")
                                    }
                                }
                            }
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
                                errorMessage = null  // Clear error message when selection changes
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
