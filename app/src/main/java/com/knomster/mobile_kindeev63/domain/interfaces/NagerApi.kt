package com.knomster.mobile_kindeev63.domain.interfaces

import com.knomster.mobile_kindeev63.domain.entities.responses.HolidayResponse

interface NagerApi {

    fun getNextHolidays(): List<HolidayResponse>?
}