package com.knomster.userslibrary.data

import com.google.gson.Gson
import com.knomster.userslibrary.domain.entities.UserDataResponse
import com.knomster.userslibrary.domain.interfaces.UsersApi

class UsersApiImpl : UsersApi {

    /**
     * Генерация случайного пользователя
     **/
    override fun generateUser(): UserDataResponse? {
        try {
            val client = UsersRetrofitClient.getClient("https://randomuser.me/")
            val usersService = client.create(UsersService::class.java)
            val call = usersService.generateUser()
            val response = call.execute()
            if (response.isSuccessful) {
                val data = response.body()?.string() ?: return null
                return Gson().fromJson(data, UserDataResponse::class.java)
            }
            return null
        } catch (_: Exception) {
            return null
        }
    }
}