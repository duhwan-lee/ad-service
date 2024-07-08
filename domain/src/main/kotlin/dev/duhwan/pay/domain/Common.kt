package dev.duhwan.pay.domain

typealias AdId = Long
typealias UserId = Long

fun String.toAdId(): AdId = this.toLong()