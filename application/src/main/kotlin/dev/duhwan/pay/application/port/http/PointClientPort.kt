package dev.duhwan.pay.application.port.http

import dev.duhwan.pay.domain.UserId

interface PointClientPort {
    suspend fun requestPointAccrual(userId: UserId, point: Int): Boolean
}