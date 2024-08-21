package com.knomster.mobile_kindeev63.data.apis.services

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NagerService {
    @GET("/api/v3/NextPublicHolidays/{lang}")
    fun getNextHolidays(
        @Path("lang") langCode: String
    ): Call<ResponseBody>
}