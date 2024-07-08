package dev.duhwan.pay.persistence.repository

import dev.duhwan.pay.persistence.config.MySqlConnectionConfig
import dev.duhwan.pay.persistence.entity.AdsEntity
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.test.context.ContextConfiguration
import java.time.LocalDateTime


@ContextConfiguration(classes = [MySqlConnectionConfig::class])
@DataR2dbcTest
class AdsRepositoryTests @Autowired constructor(
    private var adsRepository: AdsRepository
) : FunSpec({
    test("ads save") {
        val entity = AdsEntity(
            adId = null,
            adTitle = "testTitle",
            adReward = 1000,
            adMaxParticipation = 10,
            adImageUrl = "testImageUrl",
            adStartDateTime = LocalDateTime.now(),
            adEndDateTime = LocalDateTime.now().plusDays(1),
            adDescription = "testDescription",
            adParticipationCondition = null,
            adCurrentParticipation = 0
        )

        adsRepository.save(entity)
    }


    test("unique adTitle") {
        val adTitle = "testTitle"
        val entity1 = AdsEntity(
            adId = null,
            adTitle = adTitle,
            adReward = 1000,
            adMaxParticipation = 10,
            adImageUrl = "testImageUrl",
            adStartDateTime = LocalDateTime.now(),
            adEndDateTime = LocalDateTime.now().plusDays(1),
            adDescription = "testDescription",
            adParticipationCondition = null,
            adCurrentParticipation = 0
        )

        val entity2 = AdsEntity(
            adId = null,
            adTitle = adTitle,
            adReward = 1000,
            adMaxParticipation = 10,
            adImageUrl = "testImageUrl",
            adStartDateTime = LocalDateTime.now(),
            adEndDateTime = LocalDateTime.now().plusDays(1),
            adDescription = "testDescription",
            adParticipationCondition = null,
            adCurrentParticipation = 0
        )
        shouldThrowAny {
            adsRepository.save(entity1)
            adsRepository.save(entity2)
        }
    }
    test("test - updateByAdCurrentParticipationAdd"){
        val entity = AdsEntity(
            adId = null,
            adTitle = "testTitle",
            adReward = 1000,
            adMaxParticipation = 1,
            adImageUrl = "testImageUrl",
            adStartDateTime = LocalDateTime.now(),
            adEndDateTime = LocalDateTime.now().plusDays(1),
            adDescription = "testDescription",
            adParticipationCondition = null,
            adCurrentParticipation = 0
        )
        val savedEntity = adsRepository.save(entity)
        val adId = savedEntity.adId!!
        val firstAffectedRow = adsRepository.updateByAdCurrentParticipationAdd(adId)
        val secondAffectedRow = adsRepository.updateByAdCurrentParticipationAdd(adId)
        firstAffectedRow shouldBe 1L
        secondAffectedRow shouldBe 0L
    }

    test("test - updateByAdCurrentParticipationMinus"){
        val entity = AdsEntity(
            adId = null,
            adTitle = "testTitle",
            adReward = 1000,
            adMaxParticipation = 1,
            adImageUrl = "testImageUrl",
            adStartDateTime = LocalDateTime.now(),
            adEndDateTime = LocalDateTime.now().plusDays(1),
            adDescription = "testDescription",
            adParticipationCondition = null,
            adCurrentParticipation = 0
        )
        val savedEntity = adsRepository.save(entity)
        val adId = savedEntity.adId!!
        val firstAffectedRow = adsRepository.updateByAdCurrentParticipationAdd(adId)
        val secondAffectedRow = adsRepository.updateByAdCurrentParticipationAdd(adId)
        adsRepository.updateByAdCurrentParticipationMinus(adId)

        firstAffectedRow shouldBe 1L
        secondAffectedRow shouldBe 0L
        adsRepository.findById(adId)!!.adCurrentParticipation shouldBe 0L
    }
})