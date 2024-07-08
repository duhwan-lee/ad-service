package dev.duhwan.pay.persistence.entity

import dev.duhwan.pay.domain.AdId
import dev.duhwan.pay.domain.Advertisement
import dev.duhwan.pay.domain.ParticipationCondition.Companion.adParticipationConditionsToString
import dev.duhwan.pay.domain.ParticipationCondition.Companion.participationConditionFromString
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "ads")
data class AdsEntity(
    @Id val adId: AdId?,
    val adTitle: String,
    val adReward: Int,
    val adMaxParticipation: Int,
    val adCurrentParticipation: Int,
    val adDescription: String,
    val adImageUrl: String,
    val adParticipationCondition: String?,
    @Column("ad_start_datetime")
    val adStartDateTime: LocalDateTime,
    @Column("ad_end_datetime")
    val adEndDateTime: LocalDateTime,
) {
    companion object {

        fun creationFrom(advertisement: Advertisement): AdsEntity {
            return AdsEntity(
                adId = null,
                adTitle = advertisement.adTitle,
                adReward = advertisement.adReward,
                adMaxParticipation = advertisement.adMaxParticipation,
                adDescription = advertisement.adDescription,
                adImageUrl = advertisement.adImageUrl,
                adParticipationCondition = adParticipationConditionsToString(advertisement.adParticipationConditions),
                adStartDateTime = advertisement.adStartDateTime,
                adEndDateTime = advertisement.adEndDateTime,
                adCurrentParticipation = 0
            )
        }
    }

    fun toAd(): Advertisement {
        return Advertisement(
            adId = adId,
            adTitle = adTitle,
            adReward = adReward,
            adMaxParticipation = adMaxParticipation,
            adDescription = adDescription,
            adImageUrl = adImageUrl,
            adParticipationConditions = participationConditionFromString(adParticipationCondition!!),
            adStartDateTime = adStartDateTime,
            adEndDateTime = adEndDateTime
        )
    }
}
