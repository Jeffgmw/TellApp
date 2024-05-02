

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.teller.tellapp.R


@Composable
fun TransactionsPage(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Transactions",
                        color = Color.White,
                        style = TextStyle(fontSize = 24.sp),
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
        content = { paddingValues ->
            TransactionsContent(paddingValues)
        }
    )
}

@Composable
fun TransactionsContent(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(paddingValues),
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.white)
            ),
            onClick = { /*TODO*/ }) {
            Text(
                text = "View all transactions",
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Deposit Transactions",
            modifier = Modifier.padding(bottom = 8.dp),
            style = TextStyle(fontSize = 18.sp)
        )
        HeaderRow()
        DepositTransactions()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Withdrawal Transactions",
            modifier = Modifier.padding(bottom = 8.dp),
            style = TextStyle(fontSize = 18.sp)
        )
        HeaderRow()
        WithdrawalTransactions()
    }
}

@Composable
fun HeaderRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            "Name",
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold
        )
        Text(
            "Account",
            modifier = Modifier.weight(1.3f),
            fontWeight = FontWeight.Bold
        )
        Text(
            "Amount",
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold
        )
        Text(
            "Currency",
            modifier = Modifier.weight(1.3f),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            "Time",
            modifier = Modifier.weight(0.8f),
            fontWeight = FontWeight.Bold
        )
        Text(
            "Serve",
            modifier = Modifier.weight(0.8f),
            fontWeight = FontWeight.Bold
        )
        Text(
            "Type",
            modifier = Modifier.weight(0.8f),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun DepositTransactions() {
    var selectedIndex by remember { mutableStateOf(-1) } // Track selected index

    LazyColumn {
        itemsIndexed(DepositTransactionData) { index, transaction ->
            TransactionRow(
                transaction = transaction,
                onClick = { selectedIndex = index },
                isSelected = index == selectedIndex,
                selectedIndex = selectedIndex
            )
        }
    }
}

@Composable
fun WithdrawalTransactions() {
    var selectedIndex by remember { mutableStateOf(-1) } // Track selected index

    LazyColumn {
        itemsIndexed(WithdrawalTransactionData) { index, transaction ->
            TransactionRow(
                transaction = transaction,
                onClick = { selectedIndex = index },
                isSelected = index == selectedIndex,
                selectedIndex = selectedIndex
            )
        }
    }
}


@Composable
fun TransactionRow(transaction: Transaction, onClick: () -> Unit, isSelected: Boolean, selectedIndex: Int) {
    val backgroundColor = if (isSelected) Color.LightGray else Color.Transparent

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp)
            .background(if (isSelected && selectedIndex == transaction.name.toIntOrNull()) Color.DarkGray else Color.Transparent),
    ) {
        Text(
            text = transaction.name,
            modifier = Modifier.weight(1f).padding(end = 4.dp) // Add padding between columns
        )
        Text(
            text = transaction.account,
            modifier = Modifier.weight(1.3f).padding(end = 4.dp)
        )
        Text(
            text = transaction.amount,
            modifier = Modifier.weight(1f).padding(end = 4.dp)
        )
        Text(
            text = transaction.currency,
            modifier = Modifier.weight(1.3f).padding(end = 4.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = transaction.time,
            modifier = Modifier.weight(0.8f).padding(end = 4.dp)
        )
        Text(
            text = transaction.serve,
            modifier = Modifier.weight(0.8f).padding(end = 4.dp)
        )
        Text(
            text = transaction.type,
            modifier = Modifier.weight(0.8f).padding(end = 4.dp),
            textAlign = TextAlign.Center// s
        )
    }
}


data class Transaction(
    val name: String,
    val account: String,
    val amount: String,
    val currency: String,
    val time: String,
    val serve: String,
    val type: String
)

val DepositTransactionData = listOf(
    Transaction("John Doe", "123456", "$500", "KES", "10:00 AM", "Alice", "Dr"),
    Transaction("Jane Smith", "987654", "$1000", "KES", "11:30 AM", "Bob", "Cr")
)

val WithdrawalTransactionData = listOf(
    Transaction("Alice John", "555555", "$300", "KES", "9:45 AM", "Peter", "Dr"),
    Transaction("Bob Brown", "664616", "$700", "KES", "12:15 PM", "David", "Cr")
)