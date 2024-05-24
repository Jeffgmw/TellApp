
import android.content.Context
import android.os.Handler
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.JsonObject
import com.teller.tellapp.R
import com.teller.tellapp.Route
import com.teller.tellapp.data.Trans
import com.teller.tellapp.network.EntityResponse
import com.teller.tellapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EditScannedDataScreen(navController: NavController, qrCode: String) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    val textColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    // Scanned data in the format "key1:value1,key2:value2,..."
    val scannedData = qrCode.split(",").associate {
        val (key, value) = it.split(":")
        // Map key names to match the property names in the Withdraw data class
        when (key.trim()) {
            "Id" -> "id" to value.trim()
            "TransactionID" -> "transactionId" to value.trim()
            "Amount" -> "amount" to value.trim()
            "Date" -> "date" to value.trim()
//            "IsCompleted" -> "isCompleted" to value.trim()
//            "ImageData" -> "imageData" to value.trim()
//            "Account_Id" -> "accountId" to value.trim()
            "TransactionType" -> "transactionType" to value.trim()
            else -> key.trim() to value.trim()
        }
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
        },
        backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
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

            // OutlinedTextField for each key-value pair
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
                        formData = formData.toMutableMap().apply { this[key] = newValue }
                    },
                    label = { Text("") },
                    modifier = Modifier.fillMaxWidth()
                        .height(60.dp)
                        .padding(start = 24.dp, end = 24.dp),
                    enabled = index >= 0,  // Disable the first three text fields
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.DarkGray,
                        unfocusedBorderColor = Color.Gray,
                        textColor = textColor
                    ),
                    shape = RoundedCornerShape(10.dp),
                )
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
                        processTransaction(navController, context, formData)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
                ) {
                    Text("Submit")
                }
            }
        }
    }
}

// Format date string
fun formatDate(dateString: String): String {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(Date())
}

fun processTransaction(
    navController: NavController,
    context: Context,
    formData: Map<String, String>
) {
    Log.d("TransactionApproval", "Starting transaction approval...")

    Log.d("ScannedData", "Parsed data: $formData")

    // JSON object with required fields and values
    val transactionJson = JsonObject().apply {
//        addProperty("account_id", formData["accountId"].orEmpty().toLongOrNull() ?: 0L)
        addProperty("amount", formData["amount"].orEmpty().toDoubleOrNull() ?: 0.0)
//        addProperty("completed", formData["isComplete"].orEmpty().toBoolean())
        addProperty("date", formatDate(formData["date"].orEmpty()))
        addProperty("id", 0)  // Assuming ID is not relevant for approval
//        addProperty("imageData", formData["imageData"].orEmpty())
        addProperty("transactionId", formData["transactionId"].orEmpty())
        addProperty("transactionType", formData["transactionType"].toString())
    }

    Log.d(
        "TransactionApproval",
        "Data to be sent for approval: $transactionJson"
    )

    val service = RetrofitClient.instance

    // Approve API method
    val call = service.approve(transactionJson)
    call.enqueue(object : Callback<EntityResponse<Trans>> {
        override fun onResponse(
            call: Call<EntityResponse<Trans>>,
            response: Response<EntityResponse<Trans>>
        ) {
            if (response.isSuccessful) {
                val entityResponse = response.body()

                Log.d("TransactionApproval", response.body().toString())
                Log.d("TransactionApproval", "Transaction approved successfully.")

                navController.navigate(Route.HomeScreen().name)

                val toast = Toast.makeText(context, entityResponse?.message ?: "", Toast.LENGTH_LONG)
                toast.show()

                // Delay for 3 seconds and then cancel the toast
                Handler().postDelayed({
                    toast.cancel()

                }, 4000)
            } else {
                // Log approval failure
                Log.e(
                    "TransactionApproval",
                    "Transaction approval failed. Response code: ${response.code()}"
                )
            }
        }

        override fun onFailure(
            call: Call<EntityResponse<Trans>>,
            t: Throwable
        ) {
            // Log approval failure due to network error
            Log.e(
                "TransactionApproval",
                "Failed to approve transaction. Error: ${t.message}"
            )
        }
    })
}
