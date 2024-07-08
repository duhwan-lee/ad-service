package dev.duhwan.pay.persistence.adapter

import dev.duhwan.pay.application.port.persistence.AdPersistencePort
import dev.duhwan.pay.domain.AdHistory
import dev.duhwan.pay.domain.AdId
import dev.duhwan.pay.domain.Advertisement
import dev.duhwan.pay.domain.error.AdError
import dev.duhwan.pay.domain.exception.CommonAdException
import dev.duhwan.pay.persistence.entity.AdParticipationHistoryEntity
import dev.duhwan.pay.persistence.entity.AdsEntity
import dev.duhwan.pay.persistence.repository.AdParticipationHistoryRepository
import dev.duhwan.pay.persistence.repository.AdsRepository
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait

@Component
class AdPersistenceAdapter(
    private val adsRepository: AdsRepository,
    private val adParticipationHistoryRepository: AdParticipationHistoryRepository,
    transactionManager: ReactiveTransactionManager
) : AdPersistencePort {

    private val transactionalOperator = TransactionalOperator.create(transactionManager)
    private val log = logger {}

    override suspend fun saveAd(advertisement: Advertisement): AdId {
        adsRepository.findByAdTitle(adTitle = advertisement.adTitle)?.let {
            throw CommonAdException(AdError.ALREADY_REGISTERED)
        }
        val entity = AdsEntity.creationFrom(advertisement)
        val saved = adsRepository.save(entity)
        return saved.adId!!
    }

    override suspend fun findAd(adId: AdId): Advertisement? {
        return adsRepository.findById(adId)?.toAd()
    }

    override suspend fun findEnabledAds(): List<Advertisement> {
        return adsRepository.findAllEnableAds().map { it.toAd() }
    }

    override suspend fun findAdHistoryByUserId(userId: Long): List<AdId> {
        return adParticipationHistoryRepository.findAllByUserId(userId).map { it.userId }
    }

    override suspend fun findAdHistoryByUserId(userId: Long, limit: Int, page: Int): List<AdHistory> {
        val pageable = Pageable.ofSize(limit).withPage(page)
        return adParticipationHistoryRepository.findAllByUserIdOrderByParticipationDatetime(userId, pageable)
            .map { it.toAdHistory() }.toList()
    }

    override suspend fun participate(userId: Long, ad: Advertisement): Long? {
        val participationId = transactionalOperator.executeAndAwait {
            return@executeAndAwait try {
                if (adsRepository.updateByAdCurrentParticipationAdd(ad.adId!!) > 0) {
                    val entity = adParticipationHistoryRepository.save(
                        AdParticipationHistoryEntity.creationFrom(
                            adId = ad.adId!!,
                            userId = userId,
                            rewardAmount = ad.adReward,
                            adTitle = ad.adTitle
                        )
                    )
                    entity.participationId!!
                } else {
                    null
                }
            } catch (e: Exception) {
                log.error { e.printStackTrace() }
                it.setRollbackOnly()
                null
            }
        }

        return participationId
    }

    override suspend fun participationMinus(adId: AdId, participationId: Long) {
        adsRepository.updateByAdCurrentParticipationMinus(adId)
        adParticipationHistoryRepository.deleteById(participationId)
    }

}