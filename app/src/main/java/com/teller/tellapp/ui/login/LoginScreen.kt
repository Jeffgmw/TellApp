package com.teller.tellapp.ui.login

import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import com.teller.tellapp.Route
import com.teller.tellapp.data.User
import com.teller.tellapp.network.EntityResponse
import com.teller.tellapp.network.RetrofitClient
import com.teller.tellapp.ui.theme.TellAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


val defaultPadding = 16.dp
val itemSpacing = 5.dp

@Composable
fun LoginScreen(onLoginClick: () -> Unit,
                onSignUpClick: () -> Unit,
                onForgotPasswordClick: () -> Unit,
                navController: NavHostController
) {

    var showError by rememberSaveable { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    val apiService = RetrofitClient.instance

    var (userName, setUsername) = rememberSaveable {
        mutableStateOf("")
    }
    var (password, setPassword) = rememberSaveable {
        mutableStateOf("")
    }
    val (checked, onCheckedChange) = rememberSaveable {
        mutableStateOf(false)
    }
    val isFieldsEmpty = userName.isNotEmpty() && password.isNotEmpty()

    val context = LocalContext.current

    val grayEq = Color(0xFFDBD4D4)
    val maroon = Color(0xFFA42C2C)

    val textColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    val logoResource = if (isSystemInDarkTheme()) {
        R.drawable.equityjpg2
    } else {
        R.drawable.equityb
    }

    DisposableEffect(Unit) {
        onDispose {
            if (isFieldsEmpty) {
                userName = ""
                password = ""
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .clickable { keyboardController?.hide() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(70.dp))

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

        Text(
            text = "Teller Automation",
            modifier = Modifier.padding(vertical = defaultPadding),
//            style = MaterialTheme.typography.h5, // or any other suitable body text style
            style = MaterialTheme.typography.h5.copy(color = textColor)

        )

        Spacer(modifier = Modifier.height(30.dp))

        // Display the error message if showError is true -defined in the performLogin()
        if (showError) {
            Text(
                text = "Wrong credentials. Please try again.",
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        LoginTextField(
            value = userName,
            onValueChange = setUsername,
            labelText = "Username",
            leadingIcon = R.drawable.person_25,
            modifier = Modifier.fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp),
        )

        Spacer(Modifier.height(itemSpacing))

        LoginTextField(
            value = password,
            onValueChange = setPassword,
            labelText = "Password",
            leadingIcon = R.drawable.lock_25,
            modifier = Modifier.fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp),
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation(),
            showPasswordToggle = true,
        )

        Spacer(Modifier.height(itemSpacing))

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = checked, onCheckedChange = onCheckedChange)
                Text(
                    text = "Remember me",
                    color = textColor // Use the textColor here
                )
            }
            TextButton(onClick = onForgotPasswordClick) { // Use onForgotPasswordClick here
                Text(
                    text = "Forgot Password?",
                    color = textColor // Use the textColor here
                )
            }
        }
        Spacer(Modifier.height(itemSpacing))

        // Function to handle login
        fun performLogin() {
            val loginRequest = User(username = userName, password = password)
            apiService.login(loginRequest).enqueue(object : Callback<EntityResponse<User>> {
                override fun onResponse(
                    call: Call<EntityResponse<User>>,
                    response: Response<EntityResponse<User>>
                ) {
                    if (response.isSuccessful) {
                        val entityResponse = response.body()
                        if (entityResponse != null) {
                            // Handle successful login response
                            navController.navigate(Route.HomeScreen().name)
                        } else {
                            Log.d("Login", "Null response body")
                            // Handle null response body
                        }
                    } else {
                        Log.d("Login", "Unsuccessful login response: ${response.code()}")
                        // Handle unsuccessful login response
                        showError = true // Set showError to true to display the error message
                    }
                }

                override fun onFailure(call: Call<EntityResponse<User>>, t: Throwable) {
                    Log.e("Login", "Network failure: ${t.message}", t)
                    // Handle network failure
                }
            })
        }

        Button(
            onClick = { performLogin() },
            modifier = Modifier.fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp),
            shape = RoundedCornerShape(13.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (isFieldsEmpty) maroon else maroon,
                contentColor = Color.White
            ),
            enabled = isFieldsEmpty
        ) {
            Text(
                text = "Login",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Display the error message if showError is true
//        if (showError) {
//            Text(
//                text = "Wrong credentials. Please try again.",
//                color = Color.Red,
//                modifier = Modifier.padding(top = 8.dp)
//            )
//        }

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp)
        ) {
            Text(
                text = "Don't have an Account?",
                color = textColor, // Set text color here
            )
            Spacer(Modifier.width(itemSpacing))
            TextButton(onClick = onSignUpClick) {
                Text(
                    text = "Sign Up",
                    color = textColor, // Set text color here
                )
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
    }
}