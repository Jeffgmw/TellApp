package com.teller.tellapp.ui.SrcHomeScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teller.tellapp.R

val referralData = listOf(
    Referral("Jeff Top", "56123", "USD", 100.0, "2024-04-10", "user1"),
    Referral("Kim Doe", " 55789", "KES", 199.0, "2024-04-10", "user4"),
    Referral("Kev Karl", " 75789", "EUR", 500.0, "2024-04-10", "user6"),
    Referral("Pete Dave", " 85789", "GBP", 260.0, "2024-04-10", "user7"),
)

data class Referral(
    val name: String,
    val account: String,
    val currency: String,
    val amount: Double,
    val date: String,
    val user: String
)

@Preview
@Composable
fun ReferralsPage() {
    var selectedIndex by remember { mutableStateOf(-1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Referrals",
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
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                item {
                    Spacer(modifier = Modifier.size(15.dp))
                    // Table header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly // Align horizontally in the center
                    ) {
                        HeaderColumn(text = "Name")
                        HeaderColumn(text = "Account")
                        HeaderColumn(text = "Currency")
                        HeaderColumn(text = "Amount")
                        HeaderColumn(text = "Date")
                        HeaderColumn(text = "User")
                    }
                }
                // Table data
                itemsIndexed(referralData) { index, referral ->
                    DataRow(
                        referral = referral,
                        onClick = { selectedIndex = index },
                        isSelected = index == selectedIndex,
                        selectedIndex = selectedIndex
                    )
                }
            }
        }
    )
}


@Composable
fun HeaderColumn(text: String) {

    val textColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    Text(
        text = text,
        modifier = Modifier
            .padding(horizontal = 10.dp),
        style = TextStyle(fontWeight = FontWeight.Bold),
        color = textColor
    )
}

@Composable
fun DataRow(
    referral: Referral,
    onClick: () -> Unit,
    isSelected: Boolean,
    selectedIndex: Int
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
            .background(if (isSelected && selectedIndex == referral.name.toIntOrNull()) Color.DarkGray else Color.Transparent),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        DataItem(text = referral.name)
        DataItem(text = referral.account)
        DataItem(text = referral.currency)
        DataItem(text = referral.amount.toString())
        DataItem(text = referral.date)
        DataItem(text = referral.user)
    }
}

@Composable
fun DataItem(text: String) {

    val textColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    Text(
        text = text,
        modifier = Modifier
            .padding(horizontal = 10.dp),
        color = textColor
    )
}
