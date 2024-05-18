package com.teller.tellapp.ui.Home
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.teller.tellapp.R
import com.teller.tellapp.data.GenLedger
import com.teller.tellapp.network.EntityResponse
import com.teller.tellapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun TellerAndTellerGls(navController: NavController) {
    val backgroundColor = if (isSystemInDarkTheme()) {
        colorResource(id = R.color.grayEq)
    } else {
        colorResource(id = R.color.grayEq)
    }

    val textColor = if (isSystemInDarkTheme()) {
        Color.Black
    } else {
        Color.Black
    }

    var genLedger by remember { mutableStateOf<GenLedger?>(null) }
    var loading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Fetch data from the API
    LaunchedEffect(Unit) {
        val apiService = RetrofitClient.instance
        val call = apiService.getTellerGl(1)
        call.enqueue(object : Callback<EntityResponse<GenLedger>> {
            override fun onResponse(
                call: Call<EntityResponse<GenLedger>>,
                response: Response<EntityResponse<GenLedger>>
            ) {
                loading = false
                if (response.isSuccessful) {
                    val entityResponse = response.body()
                    if (entityResponse != null) {
                        genLedger = entityResponse.entity
                    } else {
                        errorMessage = "Failed to retrieve data"
                    }
                } else {
                    errorMessage = "Failed to retrieve data: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<EntityResponse<GenLedger>>, t: Throwable) {
                loading = false
                errorMessage = "Error fetching data: ${t.message}"
            }
        })
    }

    Column {
        Box(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .padding(top = 10.dp, bottom = 24.dp)
                .fillMaxWidth()
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(13.dp)
                )
        ) {
            Row {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { navController.navigate("TellerDetailsScreen") }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.darkidentity_24),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "Teller 1",
                            color = textColor,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    if (loading) {
                        CircularProgressIndicator()
                    } else if (errorMessage != null) {
                        Text(text = errorMessage ?: "Unknown error")
                    } else {
                        // Table Header
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            val headers = listOf("Currency", "GL Account", "Available bal", "Required bal")
                            headers.forEach { header ->
                                Text(
                                    text = header,
                                    color = textColor,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f) // Expand to fill available space
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // Data Rows
                        val dataRows = listOf(
                            listOf("KES", genLedger?.accno.toString(), genLedger?.balance?.toString() ?: "0.00", "100,000.00"),
                            listOf("USD", "1234567890", "8,000.00", "9,000.00"),
                            listOf("EUR", "9876543210", "3,000.00", "10,000.00"),
                            listOf("GBP", "0987654321", "2,000.00", "1,000.00")
                        )

                        dataRows.forEachIndexed { index, rowData ->
                            val availableBal = rowData[2].replace(",", "").toFloatOrNull() ?: 0f
                            val requiredBal = rowData[3].replace(",", "").toFloatOrNull() ?: 0f
                            val isAvailableBalGreaterThanRequired = availableBal > requiredBal
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clickable {
                                        when (index) {
                                            0 -> navController.navigate("Row")
                                            1 -> navController.navigate("Row")
                                            else -> {}
                                        }
                                    }
                            ) {
                                rowData.forEachIndexed { itemIndex, item ->
                                    val cellColor = when {
                                        itemIndex == 2 && isAvailableBalGreaterThanRequired -> colorResource(id = R.color.darkergreen)
                                        itemIndex == 2 && !isAvailableBalGreaterThanRequired -> colorResource(id = R.color.darkerred)
                                        else -> textColor
                                    }
                                    Text(
                                        text = item,
                                        color = cellColor,
                                        fontSize = 12.sp,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}


