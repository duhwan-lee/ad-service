package dev.duhwan.pay.application.service

import dev.duhwan.pay.application.port.http.PointClientPort
import dev.duhwan.pay.application.port.persistence.AdPersistencePort
import dev.duhwan.pay.application.usecase.AdQueryUseCase
import dev.duhwan.pay.application.usecase.AdUseCase
import dev.duhwan.pay.domain.AdId
import dev.duhwan.pay.domain.Advertisement
import dev.duhwan.pay.domain.UserId
import dev.duhwan.pay.domain.error.AdError
import dev.duhwan.pay.domain.exception.CommonAdException
import org.springframework.stereotype.Service

@Service
class AdService(
    private val adPersistencePort: AdPersistencePort,
    private val pointClientPort: PointClientPort,
    private val adQueryUseCase: AdQueryUseCase
) : AdUseCase {
    override suspend fun registerAd(advertisement: Advertisement): AdId {
        return adPersistencePort.saveAd(advertisement)
    }

    override suspend fun participation(userId: UserId, adId: AdId): Boolean {
        val ad = adQueryUseCase.findAd(adId) ?: throw CommonAdException(AdError.DOSE_NOT_EXIST_AD)
        val userAdHistories = adPersistencePort.findAdHistoryByUserId(userId)
        val disabled = ad.adParticipationConditions.any {
            !it.isApplicable(userAdHistories)
        }
        if (disabled) {
            throw CommonAdException(AdError.CAN_NOT_PARTICIPATE_AD)
        }

        val participationId = adPersistencePort.participate(userId = userId, ad = ad)

        if (participationId != null) {
            try {
                //포인트 적립 실패 시 참여 취소
                pointClientPort.requestPointAccrual(userId, ad.adReward)
            } catch (e: Exception) {
                adPersistencePort.participationMinus(adId, participationId)
                throw CommonAdException(AdError.FAIL_POINT_ACCRUAL)
            }
            return true
        } else {
            throw CommonAdException(AdError.FAIL_PARTICIPATION)
        }
    }
}