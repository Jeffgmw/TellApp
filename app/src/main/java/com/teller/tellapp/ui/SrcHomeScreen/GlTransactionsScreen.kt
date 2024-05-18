package com.teller.tellapp.ui.SrcHomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teller.tellapp.R

@Preview
@Composable
fun GLTransactionsPage() {
    val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White

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
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                    )
                },
                backgroundColor = colorResource(id = R.color.maroon),
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp, top = 4.dp)
                    .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                elevation = AppBarDefaults.TopAppBarElevation
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(10.dp)
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
                    color = textColor,
                    style = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "GL Account",
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    style = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Credit",
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    style = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Debit",
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    style = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "User",
                    fontWeight = FontWeight.Bold,
                    color = textColor,
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
                    user = transaction.user,
                    textColor = textColor
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
                    color = textColor,
                    style = MaterialTheme.typography.body1.copy(fontSize = 12.sp),
                    modifier = Modifier.weight(3.0f)
                )
                Text(
                    text = String.format("%.2f", totalCredit),
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    style = MaterialTheme.typography.body1.copy(fontSize = 12.sp),
                    modifier = Modifier.weight(1.4f)
                )
                Text(
                    text = String.format("%.2f", totalDebit),
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    style = MaterialTheme.typography.body1.copy(fontSize = 12.sp),
                    modifier = Modifier.weight(1.4f)
                )
                Text(
                    text = String.format("%.2f", balance),
                    fontWeight = FontWeight.Bold,
                    color = textColor,
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
    user: String,
    textColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = timeDate,
            fontSize = 12.sp,
            color = textColor,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .weight(1f)
                .padding(2.dp)
        )
        Text(
            text = glAccount,
            fontSize = 12.sp,
            color = textColor,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .weight(1f)
                .padding(2.dp)
        )
        Text(
            text = credit,
            fontSize = 12.sp,
            color = textColor,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .weight(1f)
                .padding(2.dp)
        )
        Text(
            text = debit,
            fontSize = 12.sp,
            color = textColor,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .weight(1f)
                .padding(2.dp)
        )
        Text(
            text = user,
            fontSize = 12.sp,
            color = textColor,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .weight(0.8f)
                .padding(2.dp)
        )
    }
}
