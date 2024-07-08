package dev.duhwan.pay.persistence.repository

import dev.duhwan.pay.persistence.entity.AdParticipationHistoryEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface AdParticipationHistoryRepository : CoroutineCrudRepository<AdParticipationHistoryEntity, Long> {
    suspend fun findAllByUserId(userId: Long): List<AdParticipationHistoryEntity>
    fun findAllByUserIdOrderByParticipationDatetime(userId: Long, pageable: Pageable): Flow<AdParticipationHistoryEntity>
}