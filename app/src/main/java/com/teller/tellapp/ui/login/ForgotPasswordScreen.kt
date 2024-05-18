package com.teller.tellapp.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.teller.tellapp.R


@Composable
fun ForgotPasswordPage(navController: NavController) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val BorderColor = Color(ContextCompat.getColor(context, R.color.maroon))

    val maroon = Color(0xFFA42C2C)

    val arrowbackicon = if (isSystemInDarkTheme()) {
        R.drawable.arrowbackwhite
    } else {
        R.drawable.arrowback
    }

    val textColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                keyboardController?.hide()
            }
            .padding(horizontal = 13.dp, vertical = 0.dp)
            .padding(start = 12.dp, end = 12.dp)
            .verticalScroll(rememberScrollState()),
    ) {

        Spacer(modifier = Modifier.height(10.dp))
        IconButton(
            onClick = {navController.popBackStack() },
            modifier = Modifier
                .width(30.dp)
                .align(Alignment.Start),
        ) {
            Icon(
                painter = painterResource(id = arrowbackicon),
                contentDescription = "Back to Login",
                tint = Color.Unspecified
            )

        }

        Spacer(modifier = Modifier.height(120.dp))

        Text(
            text = "Forgot Password",
            color = textColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Text(
            text = "Enter your Email Address",
            color = textColor,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        var emailAddress by remember { mutableStateOf("") }
        OutlinedTextField(
            value = emailAddress,
            onValueChange = { emailAddress = it },
            placeholder = { Text("Email", color = if (isSystemInDarkTheme()) Color.White else Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = textColor,
                focusedBorderColor = BorderColor,
                unfocusedBorderColor = BorderColor,
                placeholderColor = if (isSystemInDarkTheme()) Color.White else Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(100.dp))

        Button(
            onClick = { /* Handle submit action */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = maroon),
            shape = RoundedCornerShape(13.dp)
        ) {
            Text(text = "Submit",
                color = Color.White
            )
        }
    }
}
