package com.knomster.userslibrary.domain

import com.knomster.userslibrary.domain.entities.RandomUser
import com.knomster.userslibrary.domain.entities.UserData
import com.knomster.userslibrary.domain.interfaces.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersDatabaseUseCase(private val usersRepository: UsersRepository) {
    suspend fun registerUser(
        randomUser: RandomUser,
        password: String,
        onRegister: (UserData) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            val userData = UserData(
                firstName = randomUser.firstName,
                lastName = randomUser.lastName,
                email = randomUser.email,
                photoUrl = randomUser.photoUrl,
                login = randomUser.login,
                password = EncryptionHelper.encryptPassword(
                    data = password,
                    key = EncryptionHelper.generateKey(randomUser.login)
                ).replace("\n", " ")
            )
            usersRepository.insertUser(userData)
            onRegister(userData)
        }
    }

    /**
     * Вход с зашифровкой пароля
     */
    suspend fun loginUser(
        login: String,
        password: String,
        onLogin: (UserData) -> Unit,
        onFail: () -> Unit
    ) {
        withContext(Dispatchers.IO) {
            val user = usersRepository.loginUser(
                login = login,
                password = EncryptionHelper.encryptPassword(
                    data = password,
                    key = EncryptionHelper.generateKey(login)
                ).replace("\n", " ")
            )
            if (user == null) {
                onFail()
            } else {
                onLogin(user)
            }
        }
    }

    /**
     * Вход с уже зашифрованным паролем
     */
    suspend fun loginWithEncryptedPassword(
        login: String,
        encryptedPassword: String,
        onLogin: (UserData) -> Unit,
        onFail: () -> Unit
    ) {
        withContext(Dispatchers.IO) {
            val user = usersRepository.loginUser(
                login = login,
                password = encryptedPassword
            )
            if (user == null) {
                onFail()
            } else {
                onLogin(user)
            }
        }
    }

    fun checkUserLogin(login: String): Boolean {
        val logins = usersRepository.getLogins()
        return login !in logins
    }
}