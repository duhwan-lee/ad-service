package dev.duhwan.pay.domain

enum class ParticipationType {
    FIRST { //첫번째 참여 유저만
        override fun isApplicable(participatedAdIds: List<AdId>, value: String?): Boolean {
            return participatedAdIds.isEmpty()
        }

        override fun isValidValue(value: String?): Boolean = true
    },
    MORE_THEN { //n번 이상 유저만
        override fun isApplicable(participatedAdIds: List<AdId>, value: String?): Boolean {
            return participatedAdIds.size >= value!!.toInt()
        }

        override fun isValidValue(value: String?): Boolean {
            return try {
                value!!.toInt()
                true
            } catch (e: Exception) {
                false
            }
        }
    },
    SPECIFIC_ADS { //특정 광고 참여 유저만
        override fun isApplicable(participatedAdIds: List<AdId>, value: String?): Boolean {
            val adId = value!!.toAdId()
            return participatedAdIds.any { it == adId }
        }

        override fun isValidValue(value: String?): Boolean {
            return try {
                value!!.toAdId()
                true
            } catch (e: Exception) {
                false
            }
        }
    };
    //추상화된 참여 조건을 구현한다.

    abstract fun isApplicable(participatedAdIds: List<AdId>, value: String?): Boolean
    abstract fun isValidValue(value: String?): Boolean
}