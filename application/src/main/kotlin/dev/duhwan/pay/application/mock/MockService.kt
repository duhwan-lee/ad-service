package dev.duhwan.pay.application.mock

import dev.duhwan.pay.application.port.http.PointClientPort
import dev.duhwan.pay.domain.UserId
import org.springframework.stereotype.Component

@Component
class MockService: PointClientPort {
    override suspend fun requestPointAccrual(userId: UserId, point: Int): Boolean {
        return true
    }
}