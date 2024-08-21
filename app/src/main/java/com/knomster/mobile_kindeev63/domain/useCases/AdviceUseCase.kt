package com.knomster.mobile_kindeev63.domain.useCases

import com.knomster.mobile_kindeev63.domain.interfaces.AdviceApi

class AdviceUseCase(private val adviceApi: AdviceApi) {

    fun randomAdvice(): String {
        return adviceApi.randomAdvice()?.activity ?: ""
    }
}