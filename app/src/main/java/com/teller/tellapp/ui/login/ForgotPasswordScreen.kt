package com.teller.tellapp.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.teller.tellapp.R


@Composable
fun ForgotPasswordPage(navController: NavController) {

    val buttonColors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)

    val textColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }
//    val textColor2 = LocalContentColor.current

    val logoResource = if (isSystemInDarkTheme()) {
        R.drawable.equityjpg2 // Change this to the appropriate dark mode image resource
    } else {
        R.drawable.equityb // Default image resource for light mode
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 13.dp, vertical = 0.dp)
            .padding(start = 12.dp, end = 12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        Image( painter = painterResource(id = logoResource),
            contentDescription ="App Logo",
            modifier = Modifier.size(130.dp))


        // Forgot Password title
        Text(
            text = "Forgot Password",
            color = textColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Enter your address text
        Text(
            text = "Enter your Email Address",
            color = textColor,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Email Address EditText
        var emailAddress by remember { mutableStateOf("") }
        OutlinedTextField(
            value = emailAddress,
            onValueChange = { emailAddress = it },
            placeholder = { Text("Email", color = if (isSystemInDarkTheme()) Color.White else Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = textColor,
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                placeholderColor = if (isSystemInDarkTheme()) Color.White else Color.Gray
            )
        )



        // Submit Button
        Button(
            onClick = { /* Handle submit action */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = buttonColors,
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Submit")
        }

        Button(
            onClick = { navController.popBackStack() }, // Navigate back to login screen
            modifier = Modifier
                .width(140.dp)
                .align(Alignment.End),
            colors = buttonColors,
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Back to Login")
        }
    }
}
