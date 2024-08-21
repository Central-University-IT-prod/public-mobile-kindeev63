package com.knomster.mobile_kindeev63.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MainNavGraph(
    navHostController: NavHostController,
    bottomNavigationScreen: @Composable () -> Unit,
    accountScreen: @Composable () -> Unit,
    placeDetailsScreen: @Composable (String) -> Unit,
    noteEditScreen: @Composable (Int) -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.BottomNavigation.route
    ) {
        composable(Screen.BottomNavigation.route) {
            bottomNavigationScreen()
        }
        composable(
            route = Screen.PlaceDetailsScreen.route,
        ) {
            val placeId = it.arguments?.getString("placeId")
            if (placeId != null) placeDetailsScreen(placeId)
        }
        composable(
            route = Screen.NoteEditScreen.route,
        ) {
            val noteId = it.arguments?.getString("noteId")?.toInt()
            if (noteId != null) noteEditScreen(noteId)
        }
        composable(Screen.AccountScreen.route) {
            accountScreen()
        }
    }
}