package dev.duhwan.pay.api.adapter.rest

import dev.duhwan.pay.api.adapter.rest.exception.InvalidParamException
import dev.duhwan.pay.api.adapter.rest.response.CommonResponse
import dev.duhwan.pay.api.adapter.rest.response.CommonResponse.Companion.FAIL_INTERNAL
import dev.duhwan.pay.domain.exception.CommonAdException
import io.github.oshai.kotlinlogging.KotlinLogging.logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ServerWebExchange

@RestControllerAdvice
class AdControllerAdvice {
    private val log = logger {}

    @ExceptionHandler(InvalidParamException::class)
    fun invalidParamExceptionHandler(
        exception: InvalidParamException, exchange: ServerWebExchange
    ): ResponseEntity<CommonResponse> {
        log.warn { "${exception.message} - ${exchange.request.uri} " }
        return ResponseEntity(
            CommonResponse(
                resultCode = "INVALID_PARAM",
                message = exception.message,
                data = null
            ), HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(Exception::class)
    fun defaultExceptionHandler(
        exception: Exception, exchange: ServerWebExchange
    ): ResponseEntity<CommonResponse> {
        log.warn { "${exception.message} - ${exchange.request.uri} " }
        return ResponseEntity(
            CommonResponse(
                resultCode = FAIL_INTERNAL,
                message = "INTERNAL_SERVER_ERROR",
                data = null
            ), HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(CommonAdException::class)
    fun commonAdExceptionHandler(
        exception: CommonAdException, exchange: ServerWebExchange
    ): ResponseEntity<CommonResponse> {
        log.warn { "${exception.message} - ${exchange.request.uri} " }
        return ResponseEntity(
            CommonResponse(
                resultCode = exception.adError.name,
                message = exception.message,
                data = null
            ), HttpStatus.BAD_REQUEST
        )
    }
}