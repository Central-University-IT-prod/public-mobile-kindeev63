package com.knomster.mobile_kindeev63.domain.useCases

import com.knomster.mobile_kindeev63.domain.entities.DetailPlaceData
import com.knomster.mobile_kindeev63.domain.entities.database.CacheData
import com.knomster.mobile_kindeev63.domain.interfaces.CacheRepository

class CacheUseCase(private val cacheRepository: CacheRepository) {

    /**
     * Проверка актуальности кэша, удаление неактуального
     */
    suspend fun validateCache() {
        val currentTime = System.currentTimeMillis()
        val validTime = 6 * 60 * 60 * 1000
        val caches = cacheRepository.getCacheIdsAndTimes()
        caches.forEach { cache ->
            if (currentTime - cache.time > validTime) {
                cacheRepository.deleteCacheById(cache.id)
            }
        }
    }

    suspend fun insertCache(detailPlaceData: DetailPlaceData) {
        val cache = CacheData(
            id = detailPlaceData.id,
            time = System.currentTimeMillis(),
            data = detailPlaceData
        )
        cacheRepository.insertCache(cache)
    }

    suspend fun getCacheDyId(id: String): CacheData? {
        return cacheRepository.getCacheById(id = id)
    }
}