package dev.duhwan.pay.application.usecase

import dev.duhwan.pay.domain.AdId
import dev.duhwan.pay.domain.Advertisement
import dev.duhwan.pay.domain.UserId

interface AdUseCase {
    suspend fun registerAd(advertisement: Advertisement): AdId
    suspend fun participation(userId: UserId, adId: AdId): Boolean
}