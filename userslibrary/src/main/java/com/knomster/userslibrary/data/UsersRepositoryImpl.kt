package com.knomster.userslibrary.data

import com.knomster.userslibrary.domain.entities.UserData
import com.knomster.userslibrary.domain.interfaces.UsersRepository

class UsersRepositoryImpl(private val usersDao: UsersDao): UsersRepository {
    override suspend fun insertUser(userData: UserData) {
        usersDao.insertUser(userData)
    }

    override suspend fun loginUser(login: String, password: String): UserData? {
        return usersDao.loginUser(login, password)
    }

    /**
     * Получение всх существующих логинов
     */
    override fun getLogins(): List<String> {
        return usersDao.getLogins()
    }
}