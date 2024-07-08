package dev.duhwan.pay.api.adapter.rest.response

import dev.duhwan.pay.domain.AdId
import dev.duhwan.pay.domain.Advertisement

data class DisplayAd(
    val adId: AdId, val adTitle: String, val adDescription: String, val adImageUrl: String, val adReward: Int
) {
    companion object {
        fun from(advertisement: Advertisement): DisplayAd {
            return DisplayAd(
                adId = advertisement.adId!!,
                adTitle = advertisement.adTitle,
                adDescription = advertisement.adDescription,
                adImageUrl = advertisement.adImageUrl,
                adReward = advertisement.adReward
            )
        }
    }
}
