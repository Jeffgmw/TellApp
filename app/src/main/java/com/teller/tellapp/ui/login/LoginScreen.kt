package com.teller.tellapp.ui.login

import android.util.Log
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
import androidx.compose.material.MaterialTheme
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
import com.teller.tellapp.Route
import com.teller.tellapp.User
import com.teller.tellapp.network.EntityResponse
import com.teller.tellapp.network.RetrofitClient
import com.teller.tellapp.ui.components.LoginTextField
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

    val apiService = RetrofitClient.instance

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

//    val isFieldsEmpty = userName.isNotBlank() && password.isNotBlank()


    val context = LocalContext.current


    val logoResource = if (isSystemInDarkTheme()) {
        R.drawable.equityjpg2 // Change this to the appropriate dark mode image resource
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

        Text(
            text = "Teller Automation",
            modifier = Modifier.padding(vertical = defaultPadding),
            style = MaterialTheme.typography.h5 // or any other suitable body text style
        )

        Spacer(modifier = Modifier.height(30.dp))
        LoginTextField(
            value = userName,
            onValueChange = setUsername,
            labelText = "Username",
            leadingIcon = R.drawable.person_25,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(itemSpacing))


        LoginTextField(
            value = password,
            onValueChange = setPassword, // Use setPassword instead of { password = it }
            labelText = "Password",
            leadingIcon = R.drawable.lock_25,
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation(),
            showPasswordToggle = true,
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
                            // For example, you might save user data to shared preferences
                            navController.navigate(Route.HomeScreen().name)
                        } else {
                            Log.d("Login", "Null response body")
                            // Handle null response body
                        }
                    } else {
                        Log.d("Login", "Unsuccessful login response: ${response.code()}")
                        // Handle unsuccessful login response
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