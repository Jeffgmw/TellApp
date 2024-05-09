
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.teller.tellapp.R
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TransactionsPage(navController: NavController) {

    val context = LocalContext.current
    // Request permission to write to external storage
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission granted, proceed with download
                downloadTransactions(context)
            } else {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }


    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = colorResource(id = R.color.maroon),
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp, top = 4.dp)
                    .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                elevation = AppBarDefaults.TopAppBarElevation
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Transactions",
                        color = Color.White,
                        style = TextStyle(fontSize = 24.sp),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp, end = 8.dp)
                    )
                    DownloadButton(requestPermissionLauncher, Modifier.padding(end = 8.dp))
                }
            }
        },
        backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
        content = { paddingValues ->
            TransactionsContent(paddingValues)
        }
    )
}

@Composable
fun TransactionsContent(paddingValues: PaddingValues) {
    var isDepositExpanded by remember { mutableStateOf(true) }
    var isWithdrawalExpanded by remember { mutableStateOf(true) }

    val textColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    val dropdownIcon = Icons.Default.ArrowDropDown

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(paddingValues),
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth()
                .background(colorResource(id = R.color.grayEq), shape = RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(10),
           colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.grayEq),
            ),
            onClick = {
                isDepositExpanded = !isDepositExpanded
                isWithdrawalExpanded = !isWithdrawalExpanded
            }) {
            Text(
                text = "View all transactions",
                color = Color.DarkGray
            )
            Icon(
                painter = painterResource(id = R.drawable.unfold2),
                contentDescription = "Icon",
                tint = Color.Black,
                modifier = Modifier.padding(start = 12.dp)
                    .size(25.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .clickable(onClick = { isDepositExpanded = !isDepositExpanded })
                .background(colorResource(id = R.color.grayEq), shape = RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "Deposit Transactions",
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),

                style = TextStyle(fontSize = 18.sp, color = Color.DarkGray)
            )
            IconButton(
                onClick = { isDepositExpanded = !isDepositExpanded },
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Icon(
                    imageVector = dropdownIcon,
                    contentDescription = "Toggle Dropdown",
                    tint = Color.DarkGray
                )
            }
        }
        if (isDepositExpanded) {
            HeaderRow()
            DepositTransactions()
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .clickable(onClick = { isWithdrawalExpanded = !isWithdrawalExpanded })
                .background(colorResource(id = R.color.grayEq), shape = RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "Withdrawal Transactions",
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                style = TextStyle(fontSize = 18.sp, color = Color.DarkGray)
            )
            IconButton(
                onClick = { isWithdrawalExpanded = !isWithdrawalExpanded },
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Icon(
                    imageVector = dropdownIcon,
                    contentDescription = "Toggle Dropdown",
                    tint = Color.DarkGray
                )
            }
        }
        if (isWithdrawalExpanded) {
            HeaderRow()
            WithdrawalTransactions()
        }
    }
}

@Composable
fun HeaderRow() {

    val textColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            "Name",
            color = textColor,
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold

        )
        Text(
            "Account",
            color = textColor,
            modifier = Modifier.weight(1.3f),
            fontWeight = FontWeight.Bold
        )
        Text(
            "Amount",
            color = textColor,
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold
        )
        Text(
            "Currency",
            color = textColor,
            modifier = Modifier.weight(1.3f),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            "Time",
            color = textColor,
            modifier = Modifier.weight(0.8f),
            fontWeight = FontWeight.Bold
        )
        Text(
            "Serve",
            color = textColor,
            modifier = Modifier.weight(0.8f),
            fontWeight = FontWeight.Bold
        )
        Text(
            "Type",
            color = textColor,
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
    val textColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp)
            .background(if (isSelected && selectedIndex == transaction.name.toIntOrNull()) Color.DarkGray else Color.Transparent),
    ) {
        Text(
            text = transaction.name,
            color = textColor,
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp) // Add padding between columns
        )
        Text(
            text = transaction.account,
            color = textColor,
            modifier = Modifier
                .weight(1.3f)
                .padding(end = 4.dp)
        )
        Text(
            text = transaction.amount,
            color = textColor,
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
        )
        Text(
            text = transaction.currency,
            color = textColor,
            modifier = Modifier
                .weight(1.3f)
                .padding(end = 4.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = transaction.time,
            color = textColor,
            modifier = Modifier
                .weight(0.8f)
                .padding(end = 4.dp)
        )
        Text(
            text = transaction.serve,
            color = textColor,
            modifier = Modifier
                .weight(0.8f)
                .padding(end = 4.dp)
        )
        Text(
            text = transaction.type,
            color = textColor,
            modifier = Modifier
                .weight(0.8f)
                .padding(end = 4.dp),
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

@Composable
fun DownloadButton(
    requestPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    IconButton(
        onClick = {
            // Check if permission is granted
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Permission granted, proceed with download
                downloadTransactions(context)
            } else {
                // Permission not granted, request it
                requestPermissionLauncher.launch(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE).toString())
            }
        },
        modifier = modifier,
        content = {
            Icon(
                painter = painterResource(id = R.drawable.filedownload),
                contentDescription = "Download Transactions",
                tint = Color.White,
                modifier = Modifier.size(35.dp)
            )
        }
    )
}

fun downloadTransactions(context: Context) {
    val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
    val currentTimeStamp = dateFormat.format(Date())

    val fileName = "Transactions_$currentTimeStamp.pdf"

    // Get the directory for internal storage
    val directory = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)

    // Create the file object within the internal storage Downloads directory
    val file = File(directory, fileName)

    try {
        // Create a new Document
        val document = Document()

        // Create a PdfWriter instance
        PdfWriter.getInstance(document, FileOutputStream(file))

        // Open the document
        document.open()

        // Deposit transactions
        document.add(Paragraph("Deposit Transactions"))
        for (transaction in DepositTransactionData) {
            document.add(Paragraph(transaction.toString()))
        }

        // Withdrawal transactions
        document.add(Paragraph("Withdrawal Transactions"))
        for (transaction in WithdrawalTransactionData) {
            document.add(Paragraph(transaction.toString()))
        }

        // Close the document
        document.close()

        // Log the storage directory
        Log.d("DownloadTransactions", "File saved to: ${file.absolutePath}")

        // Show a success message
        Toast.makeText(context, "Transactions downloaded successfully", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        e.printStackTrace()
        // Show an error message if any exception occurs
        Toast.makeText(context, "Error downloading transactions", Toast.LENGTH_SHORT).show()
    }
}
