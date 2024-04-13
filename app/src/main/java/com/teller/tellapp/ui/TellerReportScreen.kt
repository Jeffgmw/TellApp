
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teller.tellapp.R


@Preview(showBackground = true)
@Composable
fun TellerReportsPage() {
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
                backgroundColor = Color.Gray,
                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                elevation = AppBarDefaults.TopAppBarElevation
            )
        },
        content = { paddingValues ->
            ReportsContent(paddingValues)
        }
    )
    PerformanceDashboardSection()
}

@Composable
fun ReportsContent(paddingValues: PaddingValues) {

    val buttonColors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                onClick = { /* Add action for Cash Withdrawal button */ },
                modifier = Modifier
                    .height(86.dp) //
                    .weight(1f) // Equal width
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(10.dp),
                colors = buttonColors // Set button color to gray
            ) {
                Text("Cash Withdrawal")
            }
            Button(
                onClick = {  },
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
                onClick = { /* Add action for Cash Summary button */ },
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
                onClick = { /* Add action for Cash Summary button */ },
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
    }
}

@Composable
fun PerformanceDashboardSection() {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(270.dp))

        Text(
            text = "Performance dashboard".uppercase(),
            color = Color.Black,
            fontSize = 16.sp,
        )
        Spacer(modifier = Modifier.size(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier
                    .size(228.dp)
                    .padding(start = 26.dp),
                painter = painterResource(id = R.drawable.analytics),
                contentDescription = "graph image"
            )

            // Separate child of Row
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top, // Aligns the children to the top
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Item
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(shape = CircleShape)
                            .background(
                                color = colorResource(
                                    id = R.color.gray
                                )
                            )
                    )

                    Text(
                        text = "65%",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )

                    Text(
                        text = "Above average",
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}



//@Composable
//fun ReportTab(text: String) {
//    Box(
//        modifier = Modifier
//            .width(150.dp)
//            .height(100.dp)
//            .background(Color.Gray)
//            .clickable { },
//        contentAlignment = Alignment.Center
//    ) {
//        Text(text)
//    }
//}
