package com.knomster.mobile_kindeev63.data.database

import com.knomster.mobile_kindeev63.domain.entities.database.CacheData
import com.knomster.mobile_kindeev63.domain.entities.database.CacheDataInfo
import com.knomster.mobile_kindeev63.domain.interfaces.CacheRepository

class CacheRepositoryImpl(private val cacheDao: CacheDao): CacheRepository {

    /**
     *  Получение всех кэшей, но только их id и время создания.
     *  Нужно для проверки актуальности, чтобы не получать все данные, не получаю поле data
     */
    override suspend fun getCacheIdsAndTimes(): List<CacheDataInfo> {
        return cacheDao.getCacheIdsAndTimes()
    }

    override suspend fun deleteCacheById(id: String) {
        cacheDao.deleteCacheById(id = id)
    }

    override suspend fun insertCache(cacheData: CacheData) {
        cacheDao.insertCache(cacheData = cacheData)
    }

    override suspend fun getCacheById(id: String): CacheData? {
        return cacheDao.getCacheById(id = id)
    }
}