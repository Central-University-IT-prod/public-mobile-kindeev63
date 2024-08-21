package com.knomster.userslibrary.domain

import com.knomster.userslibrary.domain.entities.RandomUser
import com.knomster.userslibrary.domain.interfaces.UsersApi

class UserGeneratorUseCase(private val usersApi: UsersApi) {

    fun generateUser(): RandomUser? {
        val user = usersApi.generateUser()?.results?.first() ?: return null
        return RandomUser(
            firstName = user.name.first,
            lastName = user.name.last,
            email = user.email,
            login = user.login.username,
            photoUrl = user.picture.large
        )
    }
}