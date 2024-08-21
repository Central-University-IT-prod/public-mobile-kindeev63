package com.knomster.userslibrary.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.knomster.userslibrary.domain.entities.UserData

@Dao
interface UsersDao {

    @Insert
    suspend fun insertUser(userData: UserData)

    @Query("SELECT * FROM table_users WHERE login = :login AND password = :password")
    suspend fun loginUser(login: String, password: String): UserData?

    @Query("SELECT login from table_users")
    fun getLogins(): List<String>

}