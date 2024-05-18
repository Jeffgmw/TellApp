package com.teller.tellapp.ui.Home
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.teller.tellapp.R
import com.teller.tellapp.data.Referral
import com.teller.tellapp.network.EntityResponse
import com.teller.tellapp.network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun ActionButtons(navController: NavController) {
    val replenishDialogState = remember { mutableStateOf(false) }
    val retrenchDialogState = remember { mutableStateOf(false) }
    val tellerId = 1L // Use the actual teller ID

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { replenishDialogState.value = true },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.grayEq)),
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .padding(end = 4.dp)
        ) {
            Text(text = "Replenish", color = Color.Black)
        }

        Button(
            onClick = { retrenchDialogState.value = true },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.grayEq)),
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .padding(start = 4.dp)
        ) {
            Text(text = "Retrench", color = Color.Black)
        }
    }

    if (replenishDialogState.value) {
        ActionDialog(
            title = "Replenish",
            tellerId = tellerId,
            onDismissRequest = { replenishDialogState.value = false }
        ) {
            replenishDialogState.value = false
        }
    }

    if (retrenchDialogState.value) {
        ActionDialog(
            title = "Retrench",
            tellerId = tellerId,
            onDismissRequest = { retrenchDialogState.value = false }
        ) {
            retrenchDialogState.value = false
        }
    }
}

@Composable
fun ActionDialog(
    title: String,
    tellerId: Long,
    onDismissRequest: () -> Unit,
    onSubmit: () -> Unit // Adjusted to take no parameters since we handle them inside the dialog
) {
    val debitGlAccountState = remember { mutableStateOf("") }
    val creditGlAccountState = remember { mutableStateOf("") }
    val amountState = remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    androidx.compose.ui.window.Dialog(onDismissRequest = { onDismissRequest() }) {
        Box(
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = debitGlAccountState.value,
                    onValueChange = { debitGlAccountState.value = it },
                    label = { Text(text = "Debit GL Account") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = creditGlAccountState.value,
                    onValueChange = { creditGlAccountState.value = it },
                    label = { Text(text = "Credit GL Account") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = amountState.value,
                    onValueChange = { amountState.value = it },
                    label = { Text(text = "Amount") },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { onDismissRequest() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(text = "Cancel", color = Color.White)
                    }

                    Button(
                        onClick = {
                            val referral = Referral(
                                admin_id = 1, // Adjust based on your requirements
                                amount = amountState.value.toDouble(),
                                completed = false,
                                destAcc = creditGlAccountState.value.toLong(),
                                id = 0, // This might be auto-generated on the server
                                referralId = "",
                                referralType = title,
                                sourceAcc = debitGlAccountState.value.toLong()
                            )

                            scope.launch {
                                val call = if (title == "Replenish") {
                                    RetrofitClient.instance.replenish(tellerId, referral)
                                } else {
                                    RetrofitClient.instance.retrench(tellerId, referral)
                                }

                                call.enqueue(object : Callback<EntityResponse<Any>> {
                                    override fun onResponse(
                                        call: Call<EntityResponse<Any>>,
                                        response: Response<EntityResponse<Any>>
                                    ) {
                                        if (response.isSuccessful) {
                                            val message = response.body()?.message ?: "$title successful"
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                            onDismissRequest()
                                        } else {
                                            val errorBody = response.errorBody()?.string()
                                            val message = errorBody ?: "Failed to $title"
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                    override fun onFailure(call: Call<EntityResponse<Any>>, t: Throwable) {
                                        Toast.makeText(context, "An error occurred: ${t.message}", Toast.LENGTH_SHORT).show()
                                    }
                                })
                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.grayEq))
                    ) {
                        Text(text = "Submit", color = Color.Black)
                    }
                }
            }
        }
    }
}
