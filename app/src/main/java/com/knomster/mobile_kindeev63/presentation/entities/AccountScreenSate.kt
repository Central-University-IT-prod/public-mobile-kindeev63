package com.knomster.mobile_kindeev63.presentation.entities

import com.knomster.userslibrary.domain.entities.RandomUser
import com.knomster.userslibrary.domain.entities.UserData

sealed class AccountScreenSate {
    data object SignIn: AccountScreenSate()
    data class SignUp(val randomUser: RandomUser? = null): AccountScreenSate()
    data class AccountInformation(val userData: UserData): AccountScreenSate()
}