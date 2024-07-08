package dev.duhwan.pay.api.adapter.rest.request

import dev.duhwan.pay.api.LocalDateTimeSerializer
import dev.duhwan.pay.api.adapter.rest.exception.InvalidParamException
import dev.duhwan.pay.domain.Advertisement
import dev.duhwan.pay.domain.ParticipationCondition
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class AdRegisterBody(
    val adTitle: String,
    val adReward: Int,
    val adMaxParticipation: Int,
    val adImageUrl: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val adStartDateTime: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val adEndDateTime: LocalDateTime,
    val adDescription: String,
    val adParticipationConditions: List<ParticipationCondition>? = null
) {
    fun toAdvertisement(): Advertisement {
        if (adParticipationConditions?.any { !it.isValidValue() } == true) {
            throw InvalidParamException("유효하지 않은 value 형식")
        }
        if (adReward !in 0..1000) {
            throw InvalidParamException("adReward는 0~1000 사이여야 합니다")
        }
        if (adMaxParticipation < 1) {
            throw InvalidParamException("adMaxParticipation은 1 이상이어야 합니다")
        }

        if(adStartDateTime.isAfter(adEndDateTime)){
            throw InvalidParamException("adStartDateTime은 adEndDateTime보다 이전이어야 합니다")
        }
        if(adEndDateTime.isBefore(LocalDateTime.now())){
            throw InvalidParamException("adEndDateTime은 현재 시간 이후여야 합니다")
        }

        return Advertisement(
            adId = null,
            adTitle = adTitle.trimStart().trimEnd(),
            adReward = adReward,
            adMaxParticipation = adMaxParticipation,
            adImageUrl = adImageUrl,
            adStartDateTime = adStartDateTime,
            adEndDateTime = adEndDateTime,
            adDescription = adDescription,
            adParticipationConditions = adParticipationConditions ?: emptyList()
        )
    }
}
