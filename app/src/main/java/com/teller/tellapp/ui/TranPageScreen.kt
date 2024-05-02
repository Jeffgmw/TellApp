package com.teller.tellapp.ui
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.teller.tellapp.R
import com.teller.tellapp.Route


//@Preview(showSystemUi = true)
@Composable
fun WithdrawalPage(
    navController: NavHostController,
    onSubmit: (WithdrawalFormData) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var accountNumber by remember { mutableStateOf("") }
    var balance by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var currency by remember { mutableStateOf("") }
    var denominations by remember { mutableStateOf("") }
    var deditAmount by remember { mutableStateOf("") }
    var tranType by remember { mutableStateOf("") }
    var valueDate by remember { mutableStateOf("") }
    var creditAmount by remember { mutableStateOf("") }
    var creditCurrency by remember { mutableStateOf("") }
    var bankCode by remember { mutableStateOf("") }
    var tranDate by remember { mutableStateOf("") }
    var branchCode by remember { mutableStateOf("") }

    val buttonColors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Transaction Page",
                        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                        color = Color.White,
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                    )
                },
                backgroundColor = colorResource(id = R.color.maroon),
                modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp)
                    .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                elevation = AppBarDefaults.TopAppBarElevation
            )
        },
        content = @androidx.compose.runtime.Composable {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                // Title
                Text(
                    text = "Customer Transaction",
                    modifier = Modifier.padding(bottom = 16.dp), // Add bottom padding to create space between the title and form fields
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                )
                Spacer(modifier = Modifier.height(10.dp))

                // Text and input fields arranged horizontally
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Name:")
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier
                            .width(260.dp)
                            .height(50.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Account Number:")
                    OutlinedTextField(
                        value = accountNumber,
                        onValueChange = { accountNumber = it },
                        modifier = Modifier
                            .width(260.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Balance:")
                    OutlinedTextField(
                        value = balance,
                        onValueChange = { balance = it },
                        modifier = Modifier
                            .width(260.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Amount:")
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        modifier = Modifier
                            .width(260.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Currency:")
                    OutlinedTextField(
                        value = currency,
                        onValueChange = { currency = it },
                        modifier = Modifier
                            .width(260.dp)
                            .height(50.dp),

                        )
                }
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Denomination:")
                    OutlinedTextField(
                        value = denominations,
                        onValueChange = { denominations = it },
                        modifier = Modifier
                            .width(260.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Debit Amount:")
                    OutlinedTextField(
                        value = deditAmount,
                        onValueChange = { deditAmount = it },
                        modifier = Modifier
                            .width(260.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Tran Type:")
                    OutlinedTextField(
                        value = tranType,
                        onValueChange = { tranType = it },
                        modifier = Modifier
                            .width(260.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Value Date:")
                    OutlinedTextField(
                        value = valueDate,
                        onValueChange = { valueDate = it },
                        modifier = Modifier
                            .width(260.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Credit Amount:")
                    OutlinedTextField(
                        value = creditAmount,
                        onValueChange = { creditAmount = it },
                        modifier = Modifier
                            .width(260.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Credit Currency:")
                    OutlinedTextField(
                        value = creditCurrency,
                        onValueChange = { creditCurrency = it },
                        modifier = Modifier
                            .width(260.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Bank Code:")
                    OutlinedTextField(
                        value = bankCode,
                        onValueChange = { bankCode = it },
                        modifier = Modifier
                            .width(260.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Branch Code:")
                    OutlinedTextField(
                        value = branchCode,
                        onValueChange = { branchCode = it },
                        modifier = Modifier
                            .width(260.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Tran Date:")
                    OutlinedTextField(
                        value = tranDate,
                        onValueChange = { tranDate = it },
                        modifier = Modifier
                            .width(260.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))



                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,

                    ) {
                    Button(
                        onClick = {
                            navController.navigate(Route.HomeScreen().name)
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = buttonColors
                    ) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(35.dp))
                    Button(
                        onClick = { onSubmit(WithdrawalFormData(name, accountNumber, balance, amount, currency,
                            denominations, deditAmount, tranType, valueDate, creditAmount, creditCurrency, bankCode, tranDate, branchCode)) },
                        shape = RoundedCornerShape(10.dp),
                        colors = buttonColors
                    ) {
                        Text("Submit")
                    }
                }
            }
        }
    )
}

data class WithdrawalFormData(
    val name: String,
    val accountNumber: String,
    val balance: String,
    val amount: String,
    val currency: String,
    val denominations:String,
    val deditAmount: String,
    val tranType: String,
    val valueDate: String,
    val creditAmount: String,
    val creditCurrency: String,
    val bankCode: String,
    val tranDate: String,
    val branchCode: String
)

@Preview(showBackground = true)
@Composable
fun PreviewWithdrawalPage() {
    val navController = rememberNavController()

    WithdrawalPage(
        navController = navController,
        onSubmit = { formData ->
        },
        onCancel = {
        }
    )
}
