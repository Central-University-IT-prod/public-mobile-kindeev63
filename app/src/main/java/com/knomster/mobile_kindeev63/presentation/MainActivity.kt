package com.knomster.mobile_kindeev63.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import com.knomster.mobile_kindeev63.presentation.navigation.MainNavGraph
import com.knomster.mobile_kindeev63.presentation.navigation.Screen
import com.knomster.mobile_kindeev63.presentation.navigation.rememberNavigationState
import com.knomster.mobile_kindeev63.presentation.screens.AccountScreen
import com.knomster.mobile_kindeev63.presentation.screens.BottomNavigationScreen
import com.knomster.mobile_kindeev63.presentation.screens.NoteEditScreen
import com.knomster.mobile_kindeev63.presentation.screens.PlaceDetailsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel = (application as MainApp).mainViewModel
        mainViewModel.validateCache()
        mainViewModel.loginWithPreferences()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.Transparent.value.toInt(),
                Color.Transparent.value.toInt()
            )
        )
        setContent {
            val navigationState = rememberNavigationState()
            MainNavGraph(
                navHostController = navigationState.navHostController,
                bottomNavigationScreen = {
                    BottomNavigationScreen(
                        mainViewModel = mainViewModel,
                        navigateToNoteEditScreen = { noteId ->
                            navigationState.navigateToNoteEdit(noteId)
                        },
                        navigateToPlaceDetailsScreen = { placeId ->
                            navigationState.navigateToPlaceDetails(placeId)
                        },
                        navigateToAccountScreen = {
                            navigationState.navigateTo(Screen.AccountScreen.route)
                        }
                    )
                },
                placeDetailsScreen = { placeId ->
                    PlaceDetailsScreen(
                        mainViewModel = mainViewModel,
                        placeId = placeId,
                        navigateToNoteEditScreen = { noteId ->
                            navigationState.navigateToNoteEdit(noteId)
                        },
                        onBackPressed = { navigationState.navHostController.popBackStack() }
                    )
                },
                noteEditScreen = { noteId ->
                    NoteEditScreen(
                        mainViewModel = mainViewModel,
                        noteId = noteId,
                        onBackPressed = { navigationState.navHostController.popBackStack() },
                        navigateToPlaceDetailsScreen = { placeId ->
                            navigationState.navigateToPlaceDetails(placeId)
                        }
                    )
                },
                accountScreen = {
                    AccountScreen(
                        mainViewModel = mainViewModel,
                        onBackPressed = { navigationState.navHostController.popBackStack() }
                    )
                }
            )
        }
    }
}