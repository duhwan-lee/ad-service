package dev.duhwan.pay.domain

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ParticipationTypeTest : FunSpec({
    test("참여조건 확인 - FIRST") {
        val participationType = ParticipationType.FIRST
        val participatedAdIds = listOf(1L, 2L)

        participationType.isApplicable(participatedAdIds, null) shouldBe false
        participationType.isApplicable(emptyList(), null) shouldBe true
    }

    test("참여조건 확인 - MORE_THEN") {
        val participationType = ParticipationType.MORE_THEN
        val participatedAdIds = listOf(1L, 2L)

        participationType.isApplicable(participatedAdIds, "2") shouldBe true
        participationType.isApplicable(participatedAdIds, "3") shouldBe false
    }

    test("참여조건 확인 - SPECIFIC_ADS") {
        val participationType = ParticipationType.SPECIFIC_ADS
        val participatedAdIds = listOf(1L, 2L)

        participationType.isApplicable(participatedAdIds, "1") shouldBe true
        participationType.isApplicable(participatedAdIds, "3") shouldBe false
    }
})
