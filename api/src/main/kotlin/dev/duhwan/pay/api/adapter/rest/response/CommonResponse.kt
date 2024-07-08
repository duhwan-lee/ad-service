package dev.duhwan.pay.api.adapter.rest.response

import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

data class CommonResponse(val resultCode: String, val message: String?, val data: Any?) {
    companion object {
        const val OK = "OK"
        const val FAIL_INTERNAL = "INTERNAL_SERVER_ERROR"
        suspend fun OK(data: Any): ServerResponse {
            return ServerResponse.ok().bodyValueAndAwait(CommonResponse(OK, null, data))
        }
    }
}