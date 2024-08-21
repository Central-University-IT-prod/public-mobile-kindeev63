package com.knomster.mobile_kindeev63.presentation.entities

import com.knomster.mobile_kindeev63.presentation.navigation.Screen
import java.io.Serializable

data class BottomNavigationBarItem(
    val id: Int,
    val screen: Screen,
    val drawableId: Int,
): Serializable
