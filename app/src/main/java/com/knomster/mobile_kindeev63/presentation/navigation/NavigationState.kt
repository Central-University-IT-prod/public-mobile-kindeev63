package com.knomster.mobile_kindeev63.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavigationState(
    val navHostController: NavHostController
) {
    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            launchSingleTop = true
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
        }
    }
    fun navigateToNoteEdit(noteId: Int) {
        navHostController.navigate(Screen.NoteEditScreen.getRouteWithArgs(noteId)) {
            launchSingleTop = true
        }
    }

    fun navigateToPlaceDetails(placeId: String) {
        navHostController.navigate(Screen.PlaceDetailsScreen.getRouteWithArgs(placeId)) {
            launchSingleTop = true
        }
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
) = remember {
    NavigationState(navHostController)
}