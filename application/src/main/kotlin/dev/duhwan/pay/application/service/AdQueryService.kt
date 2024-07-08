package dev.duhwan.pay.application.service

import dev.duhwan.pay.application.port.persistence.AdPersistencePort
import dev.duhwan.pay.application.usecase.AdQueryUseCase
import dev.duhwan.pay.domain.AdHistory
import dev.duhwan.pay.domain.AdId
import dev.duhwan.pay.domain.Advertisement
import org.springframework.stereotype.Service

@Service
class AdQueryService(
    private val adPersistencePort: AdPersistencePort
) : AdQueryUseCase{
    companion object {
        private const val DEFAULT_LIST_LIMIT = 50
    }

    override suspend fun findAd(adId: AdId): Advertisement? {
        return adPersistencePort.findAd(adId)
    }

    override suspend fun findAdByUserId(userId: Long): List<Advertisement> {
        val enableAds = adPersistencePort.findEnabledAds()
        val participatedAdIds = adPersistencePort.findAdHistoryByUserId(userId)
        val filteredList = enableAds.filter {ad ->
            !ad.adParticipationConditions.any {
                !it.isApplicable(participatedAdIds)
            }
        }
        return filteredList.sortedByDescending { it.adReward }
    }

    override suspend fun findAdHistories(userId: Long, page: Int): List<AdHistory> {
        return adPersistencePort.findAdHistoryByUserId(userId, limit = DEFAULT_LIST_LIMIT, page = page)
    }
}