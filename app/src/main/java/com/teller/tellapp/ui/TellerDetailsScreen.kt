package com.teller.tellapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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

    tellerState?.let { teller ->
        Column(modifier = Modifier.padding(16.dp, top = 50.dp)) {
            Text(text = "Teller Details:")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Name: ${teller.name}")
            Text(text = "Email: ${teller.email}")
            Text(text = "ID: ${teller.id}")
            Text(text = "National ID: ${teller.national_id}")
            Text(text = "PF Number: ${teller.pfnumber}")
        }
    }
}




