package com.teller.tellapp.ui.login

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.teller.tellapp.R
import com.teller.tellapp.ui.components.HeaderText
import com.teller.tellapp.ui.components.LoginTextField
import com.teller.tellapp.ui.theme.TellAppTheme


val defaultPadding = 16.dp
val itemSpacing = 5.dp

@Composable
fun LoginScreen(onLoginClick: () -> Unit, onSignUpClick: () -> Unit,
                onForgotPasswordClick: () -> Unit, navController: NavHostController
) {

    val (userName, setUsername) = rememberSaveable {
        mutableStateOf("")
    }
    val (password, setPassword) = rememberSaveable {
        mutableStateOf("")
    }
    val (checked, onCheckedChange) = rememberSaveable {
        mutableStateOf(false)
    }
    val isFieldsEmpty = userName.isNotEmpty() && password.isNotEmpty()

    val context = LocalContext.current


    val logoResource = if (isSystemInDarkTheme()) {
        R.drawable.eqtydarkbg // Change this to the appropriate dark mode image resource
    } else {
        R.drawable.equityb // Default image resource for light mode
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = defaultPadding, vertical = 70.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // Add your icon image here
            Image(
                painter = painterResource(id = logoResource),
                contentDescription = "Icon Image"
            )
        }

        Spacer(modifier = Modifier.height(itemSpacing))

        HeaderText(
            text = "Teller Automation",
            modifier = Modifier.padding(vertical = defaultPadding),
//            style = TextStyle(fontSize = 14.sp)
        )
        Spacer(modifier = Modifier.height(30.dp))
        LoginTextField(
            value = userName,
            onValueChange = setUsername,
            labelText = "Username",
            leadingIcon = Icons.Default.Person,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(itemSpacing))
        LoginTextField(
            value = password,
            onValueChange = setPassword,
            labelText = "Password",
            leadingIcon = Icons.Default.Lock,
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(itemSpacing))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = checked, onCheckedChange = onCheckedChange)
                Text("Remember me")
            }
            TextButton(onClick = onForgotPasswordClick) { // Use onForgotPasswordClick here
                Text("Forgot Password?")
            }
        }
        Spacer(Modifier.height(itemSpacing))
        Button(
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = isFieldsEmpty
        ) {
            Text(
                text = "Login",
                color = Color.Gray,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Don't have an Account?")
            Spacer(Modifier.width(itemSpacing))
            TextButton(onClick = onSignUpClick) {
                Text("Sign Up")
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PrevLoginScreen() {
    TellAppTheme {

        val navController = rememberNavController()

        LoginScreen(
            onLoginClick = { /*TODO*/ },
            onSignUpClick = { /*TODO*/ },
            onForgotPasswordClick = { /*TODO*/ },
            navController = navController
        )

//        LoginScreen({}, {})
    }
}