package dev.duhwan.pay.persistence.entity

import dev.duhwan.pay.domain.AdHistory
import dev.duhwan.pay.domain.AdId
import dev.duhwan.pay.domain.UserId
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "ad_participation_history")
data class AdParticipationHistoryEntity(
    @Id val participationId: Long?,
    val adId: AdId,
    val userId: UserId,
    val adTitle: String,
    val participationDatetime: LocalDateTime,
    val rewardAmount: Int
) {
    fun toAdHistory(): AdHistory {
        return AdHistory(
            adId = adId,
            userId = userId,
            adTitle = adTitle,
            participationDatetime = participationDatetime,
            rewardAmount = rewardAmount
        )
    }

    companion object {
        fun creationFrom(adId: AdId, userId: Long, adTitle: String, rewardAmount: Int): AdParticipationHistoryEntity {
            return AdParticipationHistoryEntity(
                participationId = null,
                adId = adId,
                userId = userId,
                adTitle = adTitle,
                participationDatetime = LocalDateTime.now(),
                rewardAmount = rewardAmount
            )
        }
    }
}
