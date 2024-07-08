package dev.duhwan.pay.persistence.repository

import dev.duhwan.pay.persistence.config.MySqlConnectionConfig
import dev.duhwan.pay.persistence.entity.AdParticipationHistoryEntity
import io.kotest.core.spec.style.FunSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.test.context.ContextConfiguration
import java.time.LocalDateTime

@ContextConfiguration(classes = [MySqlConnectionConfig::class])
@DataR2dbcTest
class AdParticipationHistoryRepositoryTests @Autowired constructor(
    private var adParticipationHistoryRepository: AdParticipationHistoryRepository
) : FunSpec({
    test("ad history save") {
        val entity = AdParticipationHistoryEntity(
            participationId = null,
            adId = 1,
            userId = 1,
            adTitle = "testTitle",
            participationDatetime = LocalDateTime.now(),
            rewardAmount = 1000
        )
        adParticipationHistoryRepository.save(entity)
    }
})