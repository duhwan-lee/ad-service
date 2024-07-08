package dev.duhwan.pay.domain

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ParticipationConditionTest : FunSpec({
    test("toJson / fromJson Test") {
        val firstParticipationCondition = ParticipationCondition(ParticipationType.FIRST, "1")
        val moreThanParticipationCondition = ParticipationCondition(ParticipationType.MORE_THEN, "2")
        val specificParticipationCondition = ParticipationCondition(ParticipationType.SPECIFIC_ADS, "3")

        val json = ParticipationCondition.adParticipationConditionsToString(
            listOf(
                firstParticipationCondition,
                moreThanParticipationCondition,
                specificParticipationCondition
            )
        )
        println(json)
        val fromJson = ParticipationCondition.participationConditionFromString(json)
        fromJson[0].participationType shouldBe firstParticipationCondition.participationType
        fromJson[1].participationType shouldBe moreThanParticipationCondition.participationType
        fromJson[2].participationType shouldBe specificParticipationCondition.participationType
    }

})
