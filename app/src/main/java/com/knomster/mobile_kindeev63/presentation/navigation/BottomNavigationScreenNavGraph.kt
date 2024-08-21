package com.knomster.mobile_kindeev63.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun BottomNavigationScreenNavGraph(
    navHostController: NavHostController,
    placesListScreen: @Composable () -> Unit,
    notesListScreen: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.PlacesListScreen.route
    ) {
        composable(Screen.PlacesListScreen.route) {
            placesListScreen()
        }
        composable(Screen.NotesListScreen.route) {
            notesListScreen()
        }
    }
}