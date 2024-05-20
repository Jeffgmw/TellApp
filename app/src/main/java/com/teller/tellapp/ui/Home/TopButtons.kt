package com.teller.tellapp.ui.Home
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.teller.tellapp.R
import com.teller.tellapp.Route


@Composable
fun TopButtons(navController: NavController) {

    val buttonColors = ButtonDefaults.buttonColors(
        backgroundColor = colorResource(id = R.color.grayEq)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = { navController.navigate(Route.TicketsScreen().name) },
            colors = buttonColors,
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "Tickets",
                color = Color.Black
            )
        }
        Button(
            onClick = { navController.navigate(Route.ReferralsScreen().name) },
            colors = buttonColors,
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "Referrals",
                color = Color.Black
            )
        }

        Button(
            onClick = { navController.navigate(Route.ReportsScreen().name) },
            colors = buttonColors,
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "Reports",
                color = Color.Black
            )
        }
    }
}