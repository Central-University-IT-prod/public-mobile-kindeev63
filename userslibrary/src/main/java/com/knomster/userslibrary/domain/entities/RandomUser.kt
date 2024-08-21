package com.knomster.userslibrary.domain.entities

import java.io.Serializable

data class RandomUser(
    val firstName: String,
    val lastName: String,
    val email: String,
    val photoUrl: String,
    val login: String,
): Serializable
