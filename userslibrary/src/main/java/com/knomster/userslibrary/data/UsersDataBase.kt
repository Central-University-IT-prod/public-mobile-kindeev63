package com.knomster.userslibrary.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.knomster.userslibrary.domain.entities.UserData

@Database(entities = [UserData::class], version = 1)
abstract class UsersDataBase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
    companion object {

        @Volatile
        private var INSTANCE: UsersDataBase? = null

        fun getDataBase(context: Context): UsersDataBase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = UsersDataBase::class.java,
                    name = "users.db"
                ).build()
            }
        }
    }
}