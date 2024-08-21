package com.knomster.mobile_kindeev63.domain.interfaces

import com.knomster.mobile_kindeev63.domain.entities.database.CacheData
import com.knomster.mobile_kindeev63.domain.entities.database.CacheDataInfo

interface CacheRepository {
    suspend fun getCacheIdsAndTimes(): List<CacheDataInfo>

    suspend fun deleteCacheById(id: String)

    suspend fun insertCache(cacheData: CacheData)

    suspend fun getCacheById(id: String): CacheData?
}