package com.teller.tellapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview
@Composable
fun GLTransactionsPage() {

    val textColor = if (isSystemInDarkTheme()) {
        Color.White // White color in dark mode
    } else {
        Color.Black // Black color in light mode
    }

    // Sample data for transactions
    val transactions = listOf(
        TransactionRowData("2024-04-10 10:30", "1409876536", "400,000.00", "", "Teller1"),
        TransactionRowData("2024-04-09 15:45", "1409876536", "", "200,000.00", "Teller1"),
        TransactionRowData("2024-04-09 15:45", "1409876536", "", "400,000.00", "Teller1"),
        TransactionRowData("2024-04-10 10:30", "1409876536", "50,000.00", "", "Teller1")
    )

    // Calculate total credit and debit
    var totalCredit = 0.0
    var totalDebit = 0.0
    transactions.forEach { transaction ->
        if (transaction.credit.isNotEmpty()) {
            totalCredit += transaction.credit.replace(",", "").toDouble()
        }
        if (transaction.debit.isNotEmpty()) {
            totalDebit += transaction.debit.replace(",", "").toDouble()
        }
    }

    // Calculate balance
    val balance = totalCredit - totalDebit


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "GL Transactions",
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                    )
                },
                backgroundColor = Color.Gray,
                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                elevation = AppBarDefaults.TopAppBarElevation
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .background(Color.White)
                .padding(1.dp)
                .padding(top = 10.dp)
        ) {
            // Table Header Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
            ) {
                Text(
                    text = "Time/Date",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    style = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "GL Account",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    style = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Credit",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    style = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Debit",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    style = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "User",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    style = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
                    modifier = Modifier.weight(0.8f)
                )
            }


// Data Rows
            transactions.forEach { transaction ->
                TransactionRow(
                    timeDate = transaction.timeDate,
                    glAccount = transaction.glAccount,
                    credit = transaction.credit,
                    debit = transaction.debit,
                    user = transaction.user
                )
            }

            // Balance Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp)
            ) {
                Text(
                    text = "Total",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    style = MaterialTheme.typography.body1.copy(fontSize = 12.sp),
                    modifier = Modifier.weight(3.0f)
                )
                Text(
                    text = String.format("%.2f", totalCredit),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    style = MaterialTheme.typography.body1.copy(fontSize = 12.sp),
                    modifier = Modifier.weight(1.4f)
                )
                Text(
                    text = String.format("%.2f", totalDebit),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    style = MaterialTheme.typography.body1.copy(fontSize = 12.sp),
                    modifier = Modifier.weight(1.4f)
                )
                Text(
                    text = String.format("%.2f", balance),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    style = MaterialTheme.typography.body1.copy(fontSize = 12.sp),
                    modifier = Modifier.weight(1.2f)
                )
            }

        }
    }
}

data class TransactionRowData(
    val timeDate: String,
    val glAccount: String,
    val credit: String,
    val debit: String,
    val user: String
)

@Composable
fun TransactionRow(
    timeDate: String,
    glAccount: String,
    credit: String,
    debit: String,
    user: String
) {
    val textColor = if (isSystemInDarkTheme()) {
        Color.White // White color in dark mode
    } else {
        Color.Black // Black color in light mode
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = timeDate,
            fontSize = 12.sp,
            color = Color.Black,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .weight(1f)
                .padding(2.dp)
        )
        Text(
            text = glAccount,
            fontSize = 12.sp,
            color = Color.Black,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .weight(1f)
                .padding(2.dp)
        )
        Text(
            text = credit,
            fontSize = 12.sp,
            color = Color.Black,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .weight(1f)
                .padding(2.dp)
        )
        Text(
            text = debit,
            fontSize = 12.sp,
            color = Color.Black,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .weight(1f)
                .padding(2.dp)
        )
        Text(
            text = user,
            fontSize = 12.sp,
            color = Color.Black,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .weight(0.8f)
                .padding(2.dp)
        )
    }
}