package com.knomster.mobile_kindeev63.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.knomster.mobile_kindeev63.domain.entities.database.CacheData
import com.knomster.mobile_kindeev63.domain.entities.database.Note

@Database(entities = [CacheData::class, Note::class], version = 1)
@TypeConverters(DetailPlaceDataTypeConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun cacheDao(): CacheDao

    abstract fun noteDao(): NoteDao
    companion object {

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDataBase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = AppDataBase::class.java,
                    name = "lifestyle_hub.db"
                ).build()
            }
        }
    }
}