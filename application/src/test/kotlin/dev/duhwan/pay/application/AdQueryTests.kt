package dev.duhwan.pay.application

import dev.duhwan.pay.application.port.persistence.AdPersistencePort
import dev.duhwan.pay.application.service.AdQueryService
import dev.duhwan.pay.domain.Advertisement
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime

class AdQueryTests : FunSpec({

    val mockAdPersistencePort = mockk<AdPersistencePort>()
    val adQueryService = AdQueryService(mockAdPersistencePort)

    test("findAd by AdId") {
        // Given
        val adId = 1L
        val expectedAd = Advertisement(
            adId,
            "Test Ad",
            100,
            10,
            "image",
            LocalDateTime.now(),
            LocalDateTime.now(),
            "description",
            emptyList()
        )
        coEvery { mockAdPersistencePort.findAd(adId) } returns expectedAd

        // When
        val result = runBlocking { adQueryService.findAd(adId) }

        // Then
        adId shouldBe result?.adId
    }
})