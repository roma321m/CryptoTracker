package com.roman.cryptotracker.crypto.data.mappers

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

fun ZonedDateTime.toUtcMilli(): Long {
    return this
        .withZoneSameLocal(ZoneId.of("UTC"))
        .toInstant()
        .toEpochMilli()
}

fun Long.toZonedDateTime(): ZonedDateTime {
    return Instant
        .ofEpochMilli(this)
        .atZone(ZoneId.of("UTC"))
}