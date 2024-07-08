package dev.duhwan.pay.application.usecase

import dev.duhwan.pay.domain.AdHistory
import dev.duhwan.pay.domain.AdId
import dev.duhwan.pay.domain.Advertisement

interface AdQueryUseCase {
    suspend fun findAd(adId: AdId): Advertisement?
    suspend fun findAdByUserId(userId: Long): List<Advertisement>
    suspend fun findAdHistories(userId: Long, page: Int): List<AdHistory>
}