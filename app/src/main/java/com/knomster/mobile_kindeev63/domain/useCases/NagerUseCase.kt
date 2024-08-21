package com.knomster.mobile_kindeev63.domain.useCases

import com.knomster.mobile_kindeev63.domain.entities.Holiday
import com.knomster.mobile_kindeev63.domain.interfaces.NagerApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NagerUseCase(private val nagerApi: NagerApi) {

    fun getNextHoliday(): Holiday? {
        val holiday = nagerApi.getNextHolidays()?.first() ?: return null
        val holidayDate = LocalDate.parse(holiday.date)
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        return Holiday(
            holiday.localName,
            date = holidayDate.format(formatter)
        )
    }
}