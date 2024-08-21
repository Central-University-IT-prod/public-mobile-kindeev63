package com.knomster.mobile_kindeev63.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.knomster.mobile_kindeev63.R
import com.knomster.mobile_kindeev63.domain.entities.WeatherWidgetData
import com.knomster.mobile_kindeev63.presentation.GetLocationPermission
import com.knomster.mobile_kindeev63.presentation.ui.elements.MainHeader
import com.knomster.mobile_kindeev63.presentation.ui.elements.PagePickRow
import com.knomster.mobile_kindeev63.presentation.ui.elements.PlaceItem
import com.knomster.mobile_kindeev63.presentation.viewModels.MainViewModel
import com.knomster.mobile_kindeev63.presentation.ui.elements.WeatherWidget
import com.knomster.mobile_kindeev63.presentation.viewModels.PlacesListScreenViewModel
import com.knomster.mobile_kindeev63.presentation.viewModels.PlacesListScreenViewModelFactory

@Composable
fun PlacesListScreen(
    mainViewModel: MainViewModel,
    navigateToPlaceDetailsScreen: (String) -> Unit,
    navigateToAccountScreen: () -> Unit
) {
    val viewModel: PlacesListScreenViewModel = viewModel(
        factory = PlacesListScreenViewModelFactory(mainViewModel = mainViewModel)
    )
    val weatherData by viewModel.weatherData.observeAsState(WeatherWidgetData.Load)
    val placesPages by viewModel.placesPages.observeAsState()
    val account by mainViewModel.account.observeAsState()
    if (weatherData == WeatherWidgetData.Load) {
        GetLocationPermission(
            whenGranted = { viewModel.getDataFromApis() },
            whenNotGranted = { viewModel.locationPermissionNotGranted() }
        )
    }

    // UI
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainHeader(
            name = account?.firstName,
            photoUrl = account?.photoUrl,
            goToAccount = navigateToAccountScreen
        )
        WeatherWidget(weatherData = weatherData)
        val pages = placesPages
        if (pages == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.Black
                )
            }
        } else {
            if (pages.isEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(id = R.string.no_places_nearby),
                    color = Color.Gray,
                    fontSize = (LocalConfiguration.current.screenWidthDp / 20).sp
                )
            } else {
                val currentPage by viewModel.currentPage.observeAsState(1)
                LazyColumn {
                    items(
                        items = pages[currentPage - 1].places,
                        key = { it.id }
                    ) { placeData ->
                        PlaceItem(
                            placeData = placeData,
                            onClick = {
                                navigateToPlaceDetailsScreen(placeData.id)
                            }
                        )
                    }
                    if (pages.size != 1) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                PagePickRow(
                                    width = LocalConfiguration.current.screenWidthDp / 6 * 5f,
                                    pagesCount = pages.size,
                                    currentPage = currentPage,
                                    onPickPage = { viewModel.setPage(it) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}