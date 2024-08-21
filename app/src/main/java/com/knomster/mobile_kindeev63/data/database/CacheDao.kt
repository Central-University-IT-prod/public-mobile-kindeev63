package com.knomster.mobile_kindeev63.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.knomster.mobile_kindeev63.domain.entities.database.CacheData
import com.knomster.mobile_kindeev63.domain.entities.database.CacheDataInfo

@Dao
interface CacheDao {
    @Query("SELECT id, time FROM table_cache")
    suspend fun getCacheIdsAndTimes(): List<CacheDataInfo>

    @Query("DELETE FROM table_cache WHERE id = :id")
    suspend fun deleteCacheById(id: String)

    @Insert(CacheData::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCache(cacheData: CacheData)

    @Query("SELECT * FROM table_cache WHERE id = :id")
    suspend fun getCacheById(id: String): CacheData?
}