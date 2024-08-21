package com.knomster.mobile_kindeev63.domain.entities

import java.io.Serializable

data class UserPreferenceData(
    val login: String,
    val password: String
): Serializable
