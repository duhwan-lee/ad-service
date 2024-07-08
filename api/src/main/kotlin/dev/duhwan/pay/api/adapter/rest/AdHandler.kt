package dev.duhwan.pay.api.adapter.rest

import dev.duhwan.pay.api.adapter.rest.request.AdRegisterBody
import dev.duhwan.pay.api.adapter.rest.response.CommonResponse.Companion.OK
import dev.duhwan.pay.api.adapter.rest.response.DisplayAd
import dev.duhwan.pay.application.usecase.AdQueryUseCase
import dev.duhwan.pay.application.usecase.AdUseCase
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.queryParamOrNull

@Component
class AdHandler(
    private val adUseCase: AdUseCase,
    private val adQueryUseCase: AdQueryUseCase
) {

    suspend fun registerAd(request: ServerRequest): ServerResponse {
        val body = request.awaitBody<AdRegisterBody>()
        val adId = adUseCase.registerAd(body.toAdvertisement())
        return OK(adId)
    }

    suspend fun getAds(request: ServerRequest): ServerResponse {
        val userId = request.pathVariable("userId").toLong()
        val ads = adQueryUseCase.findAdByUserId(userId).take(10).map { DisplayAd.from(it) }
        return OK(ads)
    }

    suspend fun participateAd(request: ServerRequest): ServerResponse {
        val userId = request.pathVariable("userId").toLong()
        val adId = request.pathVariable("adId").toLong()
        val result = adUseCase.participation(userId, adId)
        return OK(result)
    }

    suspend fun getHistories(request: ServerRequest): ServerResponse {
        val userId = request.pathVariable("userId").toLong()
        val page = request.queryParamOrNull("page")?.toInt() ?: 0
        val result = adQueryUseCase.findAdHistories(userId, page)
        return OK(result)
    }
}