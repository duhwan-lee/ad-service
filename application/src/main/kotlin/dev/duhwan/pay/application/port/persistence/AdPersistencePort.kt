package dev.duhwan.pay.application.port.persistence

import dev.duhwan.pay.domain.AdHistory
import dev.duhwan.pay.domain.AdId
import dev.duhwan.pay.domain.Advertisement

interface AdPersistencePort {
    suspend fun saveAd(advertisement: Advertisement): AdId
    suspend fun findAd(adId: AdId): Advertisement?
    suspend fun findEnabledAds(): List<Advertisement>
    suspend fun findAdHistoryByUserId(userId: Long): List<AdId>
    suspend fun findAdHistoryByUserId(userId: Long, limit: Int, page: Int): List<AdHistory>
    suspend fun participate(userId: Long, ad : Advertisement): Long?
    suspend fun participationMinus(adId: AdId, participationId: Long)
}