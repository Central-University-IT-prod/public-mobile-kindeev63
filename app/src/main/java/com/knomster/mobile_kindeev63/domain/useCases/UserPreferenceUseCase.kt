package com.knomster.mobile_kindeev63.domain.useCases

import android.content.SharedPreferences
import com.knomster.mobile_kindeev63.domain.entities.UserPreferenceData
import com.knomster.userslibrary.domain.EncryptionHelper

class UserPreferenceUseCase(private val userPreferences: SharedPreferences) {
    fun insertData(login: String, password: String) {
        val editor = userPreferences.edit()
        editor.putString("login", login)
        editor.putString(
            "password", EncryptionHelper.encryptPassword(
                data = password,
                key = EncryptionHelper.generateKey(login)
            ).replace("\n", " ")
        )
        editor.apply()
    }

    fun hasData() = userPreferences.contains("login")

    fun getData(): UserPreferenceData {
        val login = userPreferences.getString("login", "") ?: ""
        return UserPreferenceData(
            login = login,
            password = EncryptionHelper.decryptPassword(
                data = userPreferences.getString("password", "") ?: "",
                key = EncryptionHelper.generateKey(login)
            ),
        )
    }

    fun deleteData() {
        val editor = userPreferences.edit()
        editor.clear()
        editor.apply()
    }
}