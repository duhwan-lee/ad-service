package dev.duhwan.pay.domain

import java.time.LocalDateTime

data class Advertisement(
    var adId: AdId?,
    var adTitle: String,
    var adReward: Int,
    var adMaxParticipation: Int,
    var adImageUrl: String,
    var adStartDateTime: LocalDateTime,
    var adEndDateTime: LocalDateTime,
    var adDescription: String,
    val adParticipationConditions: List<ParticipationCondition>
) {}
