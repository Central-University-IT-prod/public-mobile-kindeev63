package com.knomster.mobile_kindeev63.domain.entities

import java.io.Serializable

data class PlaceData(
    val id: String,
    val name: String,
    val address: String,
    val photoUrl: String?,
    val categories: List<PlaceCategory>
): Serializable