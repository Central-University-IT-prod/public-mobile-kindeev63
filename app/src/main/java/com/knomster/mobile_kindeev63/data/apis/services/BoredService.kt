package com.knomster.mobile_kindeev63.data.apis.services

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface BoredService {
    @GET("api/activity/")
    fun randomAdvice(): Call<ResponseBody>
}