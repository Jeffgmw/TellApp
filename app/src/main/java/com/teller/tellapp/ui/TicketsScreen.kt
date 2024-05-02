
import androidx.compose.foundation.background
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
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
fun TicketsPage() {
    val tickets = remember { generateTicketData() }
    val textColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Tickets",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 24.sp,
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
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Table header with padding
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Wrap each table header in a Column
                    Column {
                        Text(text = "Ticket No.", fontWeight = FontWeight.Bold, color = textColor)
                    }
                    Column {
                        Text(text = "Service Type", fontWeight = FontWeight.Bold, color = textColor)
                    }
                    Column {
                        Text(text = "Amount", fontWeight = FontWeight.Bold, color = textColor)
                    }
                    Column {
                        Text(text = "Wait Time", fontWeight = FontWeight.Bold, color = textColor)
                    }
                }

                Spacer(modifier = Modifier.height(4.dp)) // Add space between header and data rows

                DataRows(tickets)

            }
        }
    )
}

@Composable
fun DataRows(tickets: List<Ticket>) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        tickets.forEach { ticket ->
            ClickableTicketRow(
                ticket = ticket,
                onClick = {},
                isSelected = false,
                selectedIndex = -1
            )
        }
    }
}


@Composable
fun ClickableTicketRow(
    ticket: Ticket,
    onClick: () -> Unit,
    isSelected: Boolean,
    selectedIndex: Int
) {
    val textColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(if (isSelected && selectedIndex == ticket.ticketNo.toInt()) Color.DarkGray else Color.Transparent),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = ticket.ticketNo, color = textColor)
        Text(text = ticket.serviceType, color = textColor)
        Text(text = ticket.amount, color = textColor)
        Text(text = ticket.waitTime, color = textColor)
    }
}

fun generateTicketData(): List<Ticket> {
    return listOf(
        Ticket("001", "Service A", "$50", "10 mins"),
        Ticket("002", "Service B", "$75", "15 mins"),
        Ticket("003", "Service C", "$100", "20 mins"),
        Ticket("004", "Service A", "$50", "10 mins"),
        Ticket("005", "Service B", "$75", "15 mins"),
        Ticket("006", "Service C", "$100", "20 mins")
    )
}

data class Ticket(
    val ticketNo: String,
    val serviceType: String,
    val amount: String,
    val waitTime: String
)

@Preview
@Composable
fun PreviewTicketsPage() {
    TicketsPage()
}
