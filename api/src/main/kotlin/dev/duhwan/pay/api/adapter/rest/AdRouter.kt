package dev.duhwan.pay.api.adapter.rest

import dev.duhwan.pay.api.adapter.rest.request.AdRegisterBody
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import kotlinx.coroutines.NonCancellable
import org.slf4j.MDC
import org.springdoc.core.annotations.RouterOperation
import org.springdoc.core.annotations.RouterOperations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.function.server.coRouter
import java.util.*

@Configuration
class AdRouter {

    @Bean
    @RouterOperations(
        value = [
            RouterOperation(
                operation = Operation(
                    operationId = "registAd",
                    responses = [ApiResponse(responseCode = "200")],
                    requestBody = RequestBody(content = [Content(schema = Schema(implementation = AdRegisterBody::class))]),
                    description = "광고 등록",
                    summary = "광고 등록"
                ),
                method = [RequestMethod.POST],
                path = "/api/v1/ads",
                beanClass = AdHandler::class,
                beanMethod = "registerAd",
            ),
            RouterOperation(
                operation = Operation(
                    operationId = "adList",
                    responses = [ApiResponse(responseCode = "200")],
                    parameters = [Parameter(
                        name = "userId", `in` = ParameterIn.PATH, description = "User Id"
                    )],
                    description = "유저별 가능 광고 조회",
                    summary = "광고 조회"
                ),
                method = [RequestMethod.GET],
                path = "/api/v1/ads/{userId}",
                beanClass = AdHandler::class,
                beanMethod = "getAds",
            ),
            RouterOperation(
                operation = Operation(
                    operationId = "participateAd",
                    responses = [ApiResponse(responseCode = "200")],
                    parameters = [Parameter(
                        name = "userId", `in` = ParameterIn.PATH, description = "User Id"
                    ), Parameter(
                        name = "adId", `in` = ParameterIn.PATH, description = "Ad Id"
                    )],
                    description = "광고 참여",
                    summary = "광고 참여"
                ),
                method = [RequestMethod.POST],
                path = "/api/v1/ads/{userId}/{adId}/participate",
                beanClass = AdHandler::class,
                beanMethod = "participateAd",
            ),
            RouterOperation(
                operation = Operation(
                    operationId = "adHistory",
                    responses = [ApiResponse(responseCode = "200")],
                    parameters = [Parameter(
                        name = "userId", `in` = ParameterIn.PATH, description = "User Id"
                    ), Parameter(
                        name = "page", `in` = ParameterIn.QUERY, description = "Page"
                    )],
                    description = "광고 참여 이력 조회",
                    summary = "광고 참여 이력 조회"
                ),
                method = [RequestMethod.GET],
                path = "/api/v1/ads/{userId}/histories",
                beanClass = AdHandler::class,
                beanMethod = "getHistories"
            )
        ]

    )

    fun adBaseRouter(adHandler: AdHandler) = coRouter {
        "/api/v1/ads".nest {
            context { _ ->
                val traceId = UUID.randomUUID().toString()
                MDC.put("traceId", "AD_$traceId")
                kotlinx.coroutines.slf4j.MDCContext() + NonCancellable
            }
            POST("", adHandler::registerAd)
            GET("/{userId}", adHandler::getAds)
            POST("/{userId}/{adId}/participate", adHandler::participateAd)
            GET("/{userId}/histories", adHandler::getHistories)
        }
    }

}
