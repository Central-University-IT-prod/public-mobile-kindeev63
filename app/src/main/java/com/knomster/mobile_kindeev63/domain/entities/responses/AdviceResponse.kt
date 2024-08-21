package com.knomster.mobile_kindeev63.domain.entities.responses

data class AdviceResponse(
    val activity: String,
    val accessibility: Double,
    val type: String,
    val participants: Int,
    val price: Double
)