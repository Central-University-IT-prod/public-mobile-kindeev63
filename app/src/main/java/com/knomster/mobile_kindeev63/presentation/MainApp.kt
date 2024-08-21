package com.knomster.mobile_kindeev63.presentation

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.knomster.mobile_kindeev63.data.database.AppDataBase
import com.knomster.mobile_kindeev63.presentation.viewModels.MainViewModel
import com.knomster.userslibrary.data.UsersDataBase

class MainApp: Application() {
    val mainViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(this)
            .create(MainViewModel::class.java)
    }
    lateinit var appDataBase: AppDataBase
    lateinit var usersDataBase: UsersDataBase

    override fun onCreate() {
        super.onCreate()
        appDataBase = AppDataBase.getDataBase(applicationContext)
        usersDataBase = UsersDataBase.getDataBase(applicationContext)
    }
}