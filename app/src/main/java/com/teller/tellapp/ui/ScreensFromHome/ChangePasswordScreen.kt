package com.teller.tellapp.ui.ScreensFromHome

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.teller.tellapp.R
import com.teller.tellapp.data.ChangePassword
import com.teller.tellapp.network.EntityResponse
import com.teller.tellapp.network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ChangePasswordScreen(navController: NavController) {
    val emailState = remember { mutableStateOf("") }
    val currentPasswordState = remember { mutableStateOf("") }
    val newPasswordState = remember { mutableStateOf("") }
    val confirmPasswordState = remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val isDarkTheme = isSystemInDarkTheme()

    val arrowbackicon = if (isSystemInDarkTheme()) {
        R.drawable.arrowbackwhite
    } else {
        R.drawable.arrowback
    }

    val backgroundColor = if (isDarkTheme) Color.Black else Color.White
    val textColor = if (isDarkTheme) Color.White else Color.Black
    val maroon = Color(0xFFA42C2C)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                painter = painterResource(id = arrowbackicon),
                contentDescription = "Back to HomeScreen",
                tint = Color.Unspecified
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top, // Align content to the top
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(90.dp))

            Text(
                text = "Change Password",
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = emailState.value,
                onValueChange = { emailState.value = it },
                label = { Text("Email Address", color = textColor) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = textColor,
                    cursorColor = textColor,
                    focusedBorderColor = textColor,
                    unfocusedBorderColor = textColor,
                    focusedLabelColor = textColor
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            PasswordTextField(
                value = currentPasswordState.value,
                onValueChange = { currentPasswordState.value = it },
                label = "Current Password",
                textColor = textColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            PasswordTextField(
                value = newPasswordState.value,
                onValueChange = { newPasswordState.value = it },
                label = "New Password",
                textColor = textColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            PasswordTextField(
                value = confirmPasswordState.value,
                onValueChange = { confirmPasswordState.value = it },
                label = "Confirm New Password",
                textColor = textColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (newPasswordState.value == confirmPasswordState.value) {
                        // Perform password change operation
                        scope.launch {
                            val changePassword = ChangePassword(
                                confirmPassword = confirmPasswordState.value,
                                emailAddress = emailState.value,
                                oldPassword = currentPasswordState.value,
                                password = newPasswordState.value
                            )
                            RetrofitClient.instance.changePassword(changePassword)
                                .enqueue(object : Callback<EntityResponse<Any>> {
                                    override fun onResponse(
                                        call: Call<EntityResponse<Any>>,
                                        response: Response<EntityResponse<Any>>
                                    ) {
                                        if (response.isSuccessful) {
                                            Toast.makeText(context, "Password changed successfully", Toast.LENGTH_SHORT).show()
                                            navController.popBackStack()
                                        } else {
                                            Toast.makeText(context, "Failed to change password", Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                    override fun onFailure(call: Call<EntityResponse<Any>>, t: Throwable) {
                                        Toast.makeText(context, "An error occurred: ${t.message}", Toast.LENGTH_SHORT).show()
                                    }
                                })
                        }
                    } else {
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = maroon)
            ) {
                Text("Submit", color = Color.White)
            }
        }
    }
}

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    textColor: Color
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = textColor) },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = textColor,
            cursorColor = textColor,
            focusedBorderColor = textColor,
            unfocusedBorderColor = textColor,
            focusedLabelColor = textColor
        ),
        trailingIcon = {
            val iconResId = if (passwordVisible) R.drawable.openeye else R.drawable.closedeye
            IconButton(onClick = {
                passwordVisible = !passwordVisible
            }) {
                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = null,
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(textColor),
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    )
}