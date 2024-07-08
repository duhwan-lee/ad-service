package dev.duhwan.pay.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class ParticipationCondition(val participationType: ParticipationType, val value: String) {
    fun isApplicable(participatedAdIds: List<AdId>): Boolean {
        return participationType.isApplicable(participatedAdIds, value)
    }

    companion object {
        private val json = Json {
            ignoreUnknownKeys = true
        }

        fun participationConditionFromString(adParticipationConditions: String): List<ParticipationCondition> {
            return json.decodeFromString<List<ParticipationCondition>>(adParticipationConditions)
        }

        fun adParticipationConditionsToString(adParticipationConditions: List<ParticipationCondition>): String {
            return Json.encodeToString(adParticipationConditions)
        }
    }

    @JsonIgnore
    fun isValidValue(): Boolean {
        return participationType.isValidValue(value)
    }
}
