
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview
@Composable
fun TicketsPage() {
    val tickets = remember { generateTicketData() }
    val ticketCounts = remember { calculateTicketCounts(tickets) }

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
                backgroundColor = Color.Gray,
                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                elevation = AppBarDefaults.TopAppBarElevation
            )
        },
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
                        .padding(horizontal = 16.dp, vertical = 8.dp), // Added padding at start and end
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Wrap each table header in a Column
                    Column {
                        Text(text = "Ticket No.", fontWeight = FontWeight.Bold, color = Color.Black)
                    }
                    Column {
                        Text(text = "Service Type", fontWeight = FontWeight.Bold, color = Color.Black)
                    }
                    Column {
                        Text(text = "Amount", fontWeight = FontWeight.Bold, color = Color.Black)
                    }
                    Column {
                        Text(text = "Wait Time", fontWeight = FontWeight.Bold, color = Color.Black)
                    }
                }

                Spacer(modifier = Modifier.height(4.dp)) // Add space between header and data rows

                // Table data
                DataRows(tickets)

                Spacer(modifier = Modifier.height(56.dp))
                // Graphical Analysis Section
                GraphicalAnalysis()

                // Bar graph representing ticket counts
                TicketBarGraph(ticketCounts)
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
                onClick = {}, // Handle click behavior here
                isSelected = false, // Pass the selection state
                selectedIndex = -1 // Pass the selected index
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() } // Call onClick callback when row is clicked
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(if (isSelected && selectedIndex == ticket.ticketNo.toInt()) Color.DarkGray else Color.Transparent),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = ticket.ticketNo)
        Text(text = ticket.serviceType)
        Text(text = ticket.amount)
        Text(text = ticket.waitTime)
    }
}


@Composable
fun TicketBarGraph(ticketCounts: Map<String, Int>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        ticketCounts.forEach { (serviceType, count) ->
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = serviceType, modifier = Modifier.width(120.dp))
                TicketBar(count)
            }
        }
    }
}

@Composable
fun TicketBar(count: Int) {
    val barHeight = count * 10 // Adjust the height of the bar as per your preference

    Spacer(
        modifier = Modifier
            .height(barHeight.dp)
            .width(16.dp)
            .background(Color.Blue)
    )
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

fun calculateTicketCounts(tickets: List<Ticket>): Map<String, Int> {
    val ticketCounts = mutableMapOf<String, Int>()

    for (ticket in tickets) {
        ticketCounts[ticket.serviceType] = ticketCounts.getOrDefault(ticket.serviceType, 0) + 1
    }

    return ticketCounts
}

data class Ticket(
    val ticketNo: String,
    val serviceType: String,
    val amount: String,
    val waitTime: String
)

@Composable
fun GraphicalAnalysis() {
    Text(
        text = "Graphical Analysis Section",
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
    )
}



@Preview
@Composable
fun PreviewTicketsPage() {
    TicketsPage()
}