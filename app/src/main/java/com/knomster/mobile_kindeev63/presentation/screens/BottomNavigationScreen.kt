package com.knomster.mobile_kindeev63.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.knomster.mobile_kindeev63.R
import com.knomster.mobile_kindeev63.presentation.ui.elements.BottomNavigationBar
import com.knomster.mobile_kindeev63.presentation.entities.BottomNavigationBarItem
import com.knomster.mobile_kindeev63.presentation.viewModels.MainViewModel
import com.knomster.mobile_kindeev63.presentation.navigation.BottomNavigationScreenNavGraph
import com.knomster.mobile_kindeev63.presentation.navigation.Screen
import com.knomster.mobile_kindeev63.presentation.navigation.rememberNavigationState

@Composable
fun BottomNavigationScreen(
    mainViewModel: MainViewModel,
    navigateToNoteEditScreen: (Int) -> Unit,
    navigateToPlaceDetailsScreen: (String) -> Unit,
    navigateToAccountScreen: () -> Unit
) {
    val navigationState = rememberNavigationState()
    val bottomNavigationBarItems = remember {
        listOf(
            BottomNavigationBarItem(0, Screen.PlacesListScreen, R.drawable.icon_place),
            BottomNavigationBarItem(1, Screen.NotesListScreen, R.drawable.icon_note)
        )
    }
    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    val selectedItemId =
        bottomNavigationBarItems.indexOf(bottomNavigationBarItems.find { item ->
            navBackStackEntry?.destination?.hierarchy?.any {
                it.route == item.screen.route
            } ?: false
        })
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.weight(1f)) {
            BottomNavigationScreenNavGraph(
                navHostController = navigationState.navHostController,
                placesListScreen = {
                    PlacesListScreen(
                        mainViewModel = mainViewModel,
                        navigateToPlaceDetailsScreen = navigateToPlaceDetailsScreen,
                        navigateToAccountScreen = navigateToAccountScreen
                    )
                },
                notesListScreen = {
                    NotesListScreen(
                        mainViewModel = mainViewModel,
                        navigateToNoteEditScreen = navigateToNoteEditScreen,
                        navigateToAccountScreen = navigateToAccountScreen
                    )
                }
            )
        }
        BottomNavigationBar(
            items = bottomNavigationBarItems,
            selectedItemId = selectedItemId
        ) { item ->
            if (item.id != selectedItemId) {
                navigationState.navigateTo(item.screen.route)
            }
        }
    }
}