package com.knomster.mobile_kindeev63.domain.entities.responses
data class HolidayResponse(
    val date: String,
    val localName: String,
    val name: String,
    val countryCode: String,
    val fixed: Boolean,
    val global: Boolean,
    val counties: List<String>,
    val launchYear: Int,
    val types: List<String>
)
