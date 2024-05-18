package com.teller.tellapp.ui.SrcHomeScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.teller.tellapp.R
import com.teller.tellapp.data.Customer
import com.teller.tellapp.network.EntityResponse
import com.teller.tellapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CustomerDetailsScreen(navController: NavController, searchQuery: String) {

    var customerDetails by remember { mutableStateOf<Customer?>(null) }
    var loading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(searchQuery) {
        val apiService = RetrofitClient.instance
        val call = apiService.searchAccount(searchQuery.toLong())
        call.enqueue(object : Callback<EntityResponse<Customer>> {
            override fun onResponse(
                call: Call<EntityResponse<Customer>>,
                response: Response<EntityResponse<Customer>>
            ) {
                loading = false
                if (response.isSuccessful) {
                    val entityResponse = response.body()
                    if (entityResponse != null) {
                        Log.d("CustomerDetailsScreen", "Response body: $entityResponse")
                        val message = entityResponse.message
                        val customer = entityResponse.entity
                        if (customer != null) {
                            Log.d("CustomerDetailsScreen", message)
                            Log.d("CustomerDetailsScreen", "Customer details retrieved successfully: $customer")
                            customerDetails = customer
                        } else {
                            errorMessage = "Failed to retrieve customer details"
                        }
                    } else {
                        errorMessage = "Failed to retrieve data"
                    }
                } else {
                    errorMessage = "Failed to retrieve data: ${response.message()}"
                    Log.e("CustomerDetailsScreen", "Failed to retrieve data: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EntityResponse<Customer>>, t: Throwable) {
                loading = false
                errorMessage = "Error fetching customer details: ${t.message}"
                Log.e("CustomerDetailsScreen", "Error fetching customer details", t)
            }
        })
    }

    // Define colors based on the current theme
    val backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White
    val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val highlightColor = colorResource(id = R.color.grayEq)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Customer Details",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(color = Color.White),
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                    )
                },
                backgroundColor = colorResource(id = R.color.maroon),
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp, top = 4.dp)
                    .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                elevation = AppBarDefaults.TopAppBarElevation
            )
        },
        backgroundColor = backgroundColor,
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (loading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (errorMessage != null) {
                    Text(
                        text = errorMessage ?: "Unknown error",
                        modifier = Modifier.padding(16.dp),
                        color = textColor
                    )
                } else if (customerDetails != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(start = 12.dp, top = 10.dp)
                    ) {
                        customerDetails?.let { customer ->
                            Column {
                                Text(
                                    text = "Name: ${customer.firstName} ${customer.lastName}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = textColor,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(highlightColor)
                                        .padding(4.dp)
                                )
                                Text(text = "First Name: ${customer.firstName}", color = textColor)
                                Text(text = "Last Name: ${customer.lastName}", color = textColor)
                                Text(text = "Email: ${customer.email}", color = textColor)
                                Text(text = "National ID: ${customer.nationalId}", color = textColor)
                                Text(text = "Phone Number: ${customer.phoneNumber}", color = textColor)

                                Spacer(modifier = Modifier.height(15.dp))

                                Text(
                                    text = "Accounts:",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = textColor,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(highlightColor)
                                        .padding(4.dp)
                                )

                                customer.customerAccount.forEach { account ->
                                    Text(text = "Account Number: ${account.accno}", color = textColor)
                                    Text(text = "Balance: ${account.balance}", color = textColor)

                                    Spacer(modifier = Modifier.height(15.dp))

                                    Text(
                                        text = "Transactions:",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = textColor,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(highlightColor)
                                            .padding(4.dp)
                                    )

                                    Column(
                                        modifier = Modifier.horizontalScroll(rememberScrollState())
                                    ) {
                                        // Table Header
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(4.dp)
                                        ) {
                                            TableCell("Transaction ID", textColor, 100.dp, FontWeight.Bold)
                                            TableCell("Amount", textColor, 70.dp, FontWeight.Bold)
                                            TableCell("Date", textColor, 150.dp, FontWeight.Bold)
                                            TableCell("Type", textColor, 70.dp, FontWeight.Bold)
                                            TableCell("Completed", textColor, 100.dp, FontWeight.Bold)
                                            TableCell("Depositer", textColor, 70.dp, FontWeight.Bold)
                                            TableCell("Depositer No", textColor, 100.dp, FontWeight.Bold)
                                            TableCell("Depositer ID", textColor, 100.dp, FontWeight.Bold)
                                        }

                                        account.transaction.forEach { transaction ->
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(4.dp)
                                            ) {
                                                TableCell(transaction.transactionId.toString(), textColor, 100.dp)
                                                TableCell(transaction.amount.toString(), textColor, 70.dp)
                                                TableCell(formatDateTime(transaction.date), textColor, 150.dp)
                                                TableCell(transaction.transactionType, textColor, 70.dp)
                                                TableCell(transaction.completed.toString(), textColor, 100.dp)
                                                TableCell(transaction.depositer ?: "", textColor, 70.dp)
                                                TableCell(transaction.depositerNo ?: "", textColor, 100.dp)
                                                TableCell(transaction.depositerId?.toString() ?: "", textColor, 100.dp)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Text(
                        text = "No customer found for the query: $searchQuery",
                        modifier = Modifier.padding(16.dp),
                        color = textColor
                    )
                }
            }
        }
    )
}

@Composable
fun TableCell(text: String, textColor: Color, width: Dp, fontWeight: FontWeight = FontWeight.Normal) {
    Text(
        text = text,
        color = textColor,
        fontWeight = fontWeight,
        modifier = Modifier
            .width(width)
            .padding(4.dp),
        maxLines = 1,
        fontSize = 14.sp,
    )
}

fun formatDateTime(dateString: String): String {
    return try {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val targetFormat = SimpleDateFormat("MMM dd, yyyy h:mm a", Locale.getDefault())
        val date = originalFormat.parse(dateString)
        date?.let { targetFormat.format(it) } ?: dateString
    } catch (e: Exception) {
        dateString
    }
}
