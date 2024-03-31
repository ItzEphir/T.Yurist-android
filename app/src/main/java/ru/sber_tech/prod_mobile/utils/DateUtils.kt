package ru.sber_tech.prod_mobile.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

object DateUtils {
    fun convertMillisToLocalDate(millis: Long) : LocalDate {
        return Instant
            .ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }
}