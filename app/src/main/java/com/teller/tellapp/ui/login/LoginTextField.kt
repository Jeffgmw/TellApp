package com.teller.tellapp.ui.login

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.teller.tellapp.R

@Composable
fun LoginTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    leadingIcon: Int? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    showPasswordToggle: Boolean = false,
    textColor: Color = Color.Black, // Default text color
    labelColor: Color = Color.Gray, // Default label color
    borderColor: Color = Color.Gray

) {
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val BorderColor = Color(ContextCompat.getColor(context, R.color.maroon))
    val maroon = Color(0xFFA42C2C)

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(labelText) },
        leadingIcon = {
            leadingIcon?.let {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = null
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (showPasswordToggle && keyboardType == KeyboardType.Password) {
            if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
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
                        .clickable { passwordVisible = !passwordVisible }
                        .size(30.dp)
                )
            }
        },
        shape = RoundedCornerShape(16.dp),
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
        leadingIcon = R.drawable.person_24

    )
}