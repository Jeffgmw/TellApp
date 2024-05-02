
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.teller.tellapp.ApiService
import com.teller.tellapp.R
import com.teller.tellapp.Route
import com.teller.tellapp.data.CustomerTransaction
import com.teller.tellapp.data.Deposit
import com.teller.tellapp.data.TransactionType
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Transaction Data",
                        style = TextStyle(color = Color.White),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                    )
                },
                backgroundColor = colorResource(id = R.color.maroon),
                modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp)
                    .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                elevation = AppBarDefaults.TopAppBarElevation
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable { keyboardController?.hide() }
                .verticalScroll(rememberScrollState())
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


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

            fun processTransaction(
                formData: Map<String, String>,
                apiService: ApiService,
                navController: NavController
            ) {
                val id = formData["id"]?.trim()?.toLong() ?: 0
                val transactionId = formData["transactionId"] ?: ""
                val amount = formData["amount"]?.toDouble() ?: 0.0
                val date = formData["date"] ?: ""
                val isCompleted = true
                val imageData = ""
                val tellerId = formData["tellerId"]?.trim()?.toLong() ?: 0
                val accountId = formData["accountId"]?.trim()?.toLong() ?: 0
                val transactionType =
                    if (formData["transactionType"] == "Deposit") TransactionType.DEPOSIT else TransactionType.WITHDRAWAL


                val customerTransaction: CustomerTransaction =
                    if (transactionType == TransactionType.DEPOSIT) {
                        Deposit(
                            id = id,
                            transactionId = transactionId,
                            amount = amount,
                            date = date,
                            isCompleted = isCompleted,
                            imageData = imageData,
                            tellerId = tellerId,
                            accountId = accountId,
                            transactionType = transactionType,
                            depositer = formData["depositer"] ?: "",
                            depositerId = formData["depositerId"] ?: "",
                            depositerNo = formData["depositerNo"] ?: ""
                        )
                    } else {
                        Withdraw(
                            id = id,
                            transactionId = transactionId,
                            amount = amount,
                            date = date,
                            isCompleted = isCompleted,
                            imageData = imageData,
                            tellerId = tellerId,
                            accountId = accountId,
                            transactionType = transactionType
                        )
                    }

                apiService.approve(id, customerTransaction)
                    .enqueue(object : Callback<EntityResponse<CustomerTransaction>> {
                        override fun onResponse(
                            call: Call<EntityResponse<CustomerTransaction>>,
                            response: Response<EntityResponse<CustomerTransaction>>
                        ) {
                            if (response.isSuccessful) {
                                val entityResponse = response.body()
                                if (entityResponse != null && entityResponse.statusCode == 200) {
                                    // Log success
                                    Log.d("Transaction", "Transaction approval successful")
                                    // Optionally, you can navigate to another screen upon success
                                    navController.navigate(Route.HomeScreen().name)
                                    // Display true indicating success
                                    println(true)
                                } else {
                                    // Log failure
                                    Log.d(
                                        "Transaction",
                                        "Failed to approve transaction: ${entityResponse?.message ?: "Unknown error"}"
                                    )
                                    // Display false indicating failure
                                    println(false)
                                }
                            } else {
                                // Log failure
                                Log.d(
                                    "Transaction",
                                    "Error: ${response.code()} - ${response.message()}"
                                )
                                // Display false indicating failure
                                println(false)
                            }
                        }

                        override fun onFailure(
                            call: Call<EntityResponse<CustomerTransaction>>,
                            t: Throwable
                        ) {
                            // Log failure
                            Log.e(
                                "Transaction",
                                "Failed to approve transaction: ${t.message ?: "Unknown error"}",
                                t
                            )
                            // Display false indicating failure
                            println(false)
                        }
                    })
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Button(
                    onClick = {
                        navController.navigate(Route.HomeScreen().name)
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        processTransaction(formData, apiService, navController)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
                ) {
                    Text("Submit")
                }
            }
        }
    }
}


