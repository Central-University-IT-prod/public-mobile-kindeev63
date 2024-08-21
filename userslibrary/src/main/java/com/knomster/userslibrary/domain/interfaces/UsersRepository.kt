package com.knomster.userslibrary.domain.interfaces

import com.knomster.userslibrary.domain.entities.UserData

interface UsersRepository {
    suspend fun insertUser(userData: UserData)

    suspend fun loginUser(login: String, password: String): UserData?

    fun getLogins(): List<String>
}