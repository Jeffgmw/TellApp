package com.teller.tellapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teller.tellapp.R

@Composable
fun LoginTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    leadingIcon: Int? = null, // Change to Int to accept drawable resource IDs
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    showPasswordToggle: Boolean = false,
) {
    var passwordVisible by remember { mutableStateOf(false) } // State variable to track password visibility
    val context = LocalContext.current

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(), // Ensure the TextField fills the available width
        value = value,
        onValueChange = onValueChange,
        label = { Text(labelText) },
        leadingIcon = {
            leadingIcon?.let {
                Icon(
                    painter = painterResource(id = it), // Load drawable resource
                    contentDescription = null
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (showPasswordToggle && keyboardType == KeyboardType.Password) {
            if (passwordVisible) {
                VisualTransformation.None // Show password
            } else {
                PasswordVisualTransformation() // Hide password
            }
        } else {
            visualTransformation
        },

        trailingIcon = {
            if (showPasswordToggle && keyboardType == KeyboardType.Password) {
                val eyeIcon = if (passwordVisible) R.drawable.openeye else R.drawable.closedeye
                Icon(
                    painter = painterResource(id = eyeIcon),
                    contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                    modifier = Modifier
                        .clickable { passwordVisible = !passwordVisible } // Toggle password visibility
                        .size(30.dp) // Set the size of the icon
                )
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PrevTextField() {
    LoginTextField(
        value = "",
        onValueChange = {},
        labelText = "Password",
        keyboardType = KeyboardType.Password,
        showPasswordToggle = true,
        leadingIcon = R.drawable.person_24 // Example of passing a drawable resource ID
    )
}