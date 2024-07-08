package dev.duhwan.pay.domain

import java.time.LocalDateTime

data class AdHistory(
    val adId: AdId,
    val userId : UserId,
    val adTitle : String,
    val participationDatetime : LocalDateTime,
    val rewardAmount:Int
) {}