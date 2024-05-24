package com.teller.tellapp.ui.SrcHomeScreen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.teller.tellapp.R
import com.teller.tellapp.ViewModels.ReferralsViewModel


@Composable
fun ReferralsPage(referralsViewModel: ReferralsViewModel = viewModel()) {
    val referrals by referralsViewModel.referralsTrans

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "All Referrals",
                        style = TextStyle(color = Color.White),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                    )
                },
                backgroundColor = colorResource(id = R.color.maroon),
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp, top = 4.dp)
                    .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                elevation = AppBarDefaults.TopAppBarElevation
            )
        },
        backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
        content = { paddingValues ->
            Box(modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .horizontalScroll(rememberScrollState())
            ) {
                LazyColumn {
                    item {
                        Spacer(modifier = Modifier.size(10.dp))

                        // Table header
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            HeaderColumn(text = "ReferralId")
                            HeaderColumn(text = "ReferralType")
                            HeaderColumn(text = "Amount")
                            HeaderColumn(text = "Completed")
                            HeaderColumn(text = "Date")
                            HeaderColumn(text = "SourceAcc")
                            HeaderColumn(text = "DestAcc")
                        }
                    }

                    items(referrals) { referral ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            TableCell(text = referral.referralId ?: "NULL")
                            TableCell(text = referral.referralType ?: "NULL")
                            TableCell(text = referral.amount.toString())
                            TableCell(text = referral.completed.toString())
                            TableCell(text = referral.date ?: "NULL")
                            TableCell(text = referral.sourceAcc.toString())
                            TableCell(text = referral.destAcc.toString())
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun HeaderColumn(text: String) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(100.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
        )
    }
}

@Composable
fun TableCell(text: String) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(100.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
        )
    }
}
