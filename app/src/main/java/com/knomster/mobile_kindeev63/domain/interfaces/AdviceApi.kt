package com.knomster.mobile_kindeev63.domain.interfaces

import com.knomster.mobile_kindeev63.domain.entities.responses.AdviceResponse

interface AdviceApi {

    fun randomAdvice(): AdviceResponse?
}