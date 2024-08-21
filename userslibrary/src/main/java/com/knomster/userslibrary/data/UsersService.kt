package com.knomster.userslibrary.data

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface UsersService {
    @GET("api/")
    fun generateUser(): Call<ResponseBody>
}