package com.teller.tellapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.teller.tellapp.R
import com.teller.tellapp.data.Teller
import com.teller.tellapp.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun TellerDetails(navController: NavController) {
    var tellerState by remember { mutableStateOf<Teller?>(null) }

    LaunchedEffect(true) {
        val response = withContext(Dispatchers.IO) {
            RetrofitClient.instance.getTellerDetails().execute()
        }
        if (response.isSuccessful) {
            val entityResponse = response.body()
            if (entityResponse != null) {
                tellerState = entityResponse.entity
            }
        }
    }

    Scaffold(
        topBar = { TellerDetailsTopBar() }
    ) { innerPadding ->
        tellerState?.let { teller ->
            TellerDetailsContent(teller, innerPadding)
        }
    }
}

@Composable
fun TellerDetailsTopBar() {
    TopAppBar(
        title = {
            Text(
                "Teller Details",
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
}

@Composable
fun TellerDetailsContent(teller: Teller, padding: PaddingValues) {
    Column(modifier = Modifier.padding(padding).padding(start = 12.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Name: ${teller.name}")
        Text(text = "Email: ${teller.email}")
        Text(text = "ID: ${teller.id}")
        Text(text = "National ID: ${teller.national_id}")
        Text(text = "PF Number: ${teller.pfnumber}")
    }
}


