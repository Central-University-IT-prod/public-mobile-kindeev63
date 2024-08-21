package com.knomster.mobile_kindeev63.presentation.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.knomster.mobile_kindeev63.R
import com.knomster.mobile_kindeev63.domain.entities.Holiday
import com.knomster.mobile_kindeev63.presentation.viewModels.MainViewModel

@Composable
fun AdviceAndHolidays(
    mainViewModel: MainViewModel
) {
    val height = LocalConfiguration.current.screenWidthDp / 5 * 2 + 20
    var randomActivity by rememberSaveable { mutableStateOf("") }
    var holiday by rememberSaveable { mutableStateOf<Holiday?>(null) }
    LaunchedEffect(Unit) {
        mainViewModel.randomAdvice { randomActivity = it }
        mainViewModel.getNextHoliday { holiday = it }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp)
            .padding(10.dp),
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            holiday?.let { holidayData ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .padding(3.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Text(
                        modifier = Modifier.padding(start = 20.dp),
                        text = stringResource(id = R.string.next_holiday),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        modifier = Modifier.padding(start = 30.dp),
                        text = "${holidayData.name} ${holidayData.date}"
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(3.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = stringResource(id = R.string.random_activity),
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { mainViewModel.randomAdvice { randomActivity = it } }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh activity icon",
                            tint = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = randomActivity)
                }
            }
        }
    }
}