
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.teller.tellapp.R
import com.teller.tellapp.Route


@Composable
fun ReportsPage(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Reports",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(fontSize = 24.sp),
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                    )
                },
                backgroundColor = colorResource(id = R.color.maroon),
                modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp)
                    .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                elevation = AppBarDefaults.TopAppBarElevation
            )
        },
        content = {
            ReportsContent(navController = navController, paddingValues = it)
        }
    )
}

@Composable
fun ReportsContent(navController: NavController, paddingValues: PaddingValues) {

    val buttonColors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.grayEq))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(paddingValues),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    println("Before navigation to TransactionsScreen")
                    navController.navigate(Route.TransactionsScreen().name)
                    println("After navigation to TransactionsScreen")
                },
                modifier = Modifier
                    .height(86.dp)
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(10.dp),
                colors = buttonColors
            ) {
                Text("Cash Withdrawal")
            }
            Button(
                onClick = {navController.navigate(Route.TransactionsScreen().name) },
                modifier = Modifier
                    .height(86.dp)
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(10.dp),
                colors = buttonColors
            ) {
                Text("Cash Deposit")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {  },
                modifier = Modifier
                    .height(86.dp)
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(10.dp),
                colors = buttonColors
            ) {
                Text("Cash Hdentill")
            }
            Button(
                onClick = { navController.navigate(Route.TransactionsScreen().name) },
                modifier = Modifier
                    .height(86.dp)
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(10.dp),
                colors = buttonColors
            ) {
                Text("Cash Summary")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {  },
                modifier = Modifier
                    .height(86.dp)
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(10.dp),
                colors = buttonColors
            ) {
                Text("Service Report")
            }
            Button(
                onClick = { /* Action for Cash Summary button */ },
                modifier = Modifier
                    .height(86.dp)
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(10.dp),
                colors = buttonColors
            ) {
                Text("GLs Reports")
            }
        }
    }
}

