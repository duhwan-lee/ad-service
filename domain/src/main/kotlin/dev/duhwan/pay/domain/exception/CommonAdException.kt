package dev.duhwan.pay.domain.exception

import dev.duhwan.pay.domain.error.AdError

class CommonAdException(val adError: AdError) : RuntimeException(adError.message) {

}