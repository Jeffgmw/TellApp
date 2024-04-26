
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.teller.tellapp.ApiService
import com.teller.tellapp.Route
import com.teller.tellapp.data.CustomerTransaction
import com.teller.tellapp.data.Deposit
import com.teller.tellapp.data.Withdraw
import com.teller.tellapp.network.EntityResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun EditScannedDataScreen(navController: NavController, qrCode: String, apiService: ApiService) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val textColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    // Assuming the scanned data is in the format "key1:value1,key2:value2,..."
    val scannedData = qrCode.split(",").associate {
        val (key, value) = it.split(":")
        key to value
    }

    var formData by remember { mutableStateOf(scannedData.toMutableMap()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { keyboardController?.hide() }
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Transaction Data",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Create an OutlinedTextField for each key-value pair
        formData.entries.forEachIndexed { index, entry ->
            val (key, value) = entry
            var fieldValue by remember { mutableStateOf(value) }

            Text(
                text = key,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp),
                color = textColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = fieldValue,
                onValueChange = { newValue ->
                    fieldValue = newValue
                    formData[key] = newValue
                },
                label = { Text("") },
                modifier = Modifier.fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 24.dp, end = 24.dp),
                enabled = index >= 6,  // Disable the six three text fields
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.DarkGray,
                    unfocusedBorderColor = Color.Gray,
                    textColor = textColor
                ),
                shape = RoundedCornerShape(10.dp),
            )
        }

        Button(
            onClick = {
                // Combine the edited values with their keys
                val editedData = formData.map { "${it.key}:${it.value}" }.joinToString(",")
                // Approve the transaction based on the type
                val transaction = if (formData["transactionType"] == "Deposit") {
                    // Create a Deposit object
                    Deposit(
                        id = formData["id"]?.toLong() ?: 0,
                        transactionId = formData["transactionId"] ?: "",
                        amount = formData["amount"]?.toDouble() ?: 0.0,
                        date = formData["date"] ?: "",
                        isCompleted = true,
                        imageData = "",
                        depositer = formData["depositer"] ?: "",
                        depositerId = formData["depositerId"] ?: "",
                        depositerNo = formData["depositerNo"] ?: ""
                    )
                } else {
                    // Create a Withdraw object
                    Withdraw(
                        id = formData["id"]?.toLong() ?: 0,
                        transactionId = formData["transactionId"] ?: "",
                        amount = formData["amount"]?.toDouble() ?: 0.0,
                        date = formData["date"] ?: "",
                        isCompleted = true,
                        imageData = ""
                    )
                }
                // Call the approve API
                apiService.approve(transaction)
                    .enqueue(object : Callback<EntityResponse<CustomerTransaction>> {
                        override fun onResponse(
                            call: Call<EntityResponse<CustomerTransaction>>,
                            response: Response<EntityResponse<CustomerTransaction>>
                        ) {
                            // Handle success response
                            if (response.isSuccessful) {
                                // Navigate to a different screen upon success
                                navController.navigate(Route.TransPageScreen().name) // Replace "destination_route" with the actual route of the destination screen
                            } else {
                                // Handle error response
                            }
                        }

                        override fun onFailure(
                            call: Call<EntityResponse<CustomerTransaction>>,
                            t: Throwable
                        ) {
                            // Handle failure
                        }
                    })
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
        ) {
            Text("Submit")
        }
    }
}


