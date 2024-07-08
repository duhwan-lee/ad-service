package dev.duhwan.pay.domain.error

enum class AdError(val message: String) {
    DOSE_NOT_EXIST_AD("광고가 존재하지 않습니다."),
    CAN_NOT_PARTICIPATE_AD("참여할 수 없는 광고입니다."),
    FAIL_POINT_ACCRUAL("포인트 적립에 실패하였습니다."),
    FAIL_PARTICIPATION("참여에 실패하였습니다."),
    ALREADY_REGISTERED("이미 등록된 광고입니다."),
}