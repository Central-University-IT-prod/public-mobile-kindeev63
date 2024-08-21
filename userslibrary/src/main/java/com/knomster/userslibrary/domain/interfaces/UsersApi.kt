package com.knomster.userslibrary.domain.interfaces

import com.knomster.userslibrary.domain.entities.UserDataResponse

interface UsersApi {
    fun generateUser(): UserDataResponse?
}