package com.kabigon.weatherforecast.usecase


import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.concurrent.TimeUnit


class TimezoneMapper {
    operator fun invoke(timeStamp: Long, offset: Long): List<String> {
        // 1. Create an Instant from the UTC timestamp (dt)
        val utcInstant = Instant.ofEpochSecond(timeStamp)

        // 2. Create a ZoneId from the timezone offset
        // The ZoneId.ofOffset requires a prefix "GMT", "UTC", or "UT"
        // and the offset string like "+02:00"
        val hours = TimeUnit.SECONDS.toHours(offset)
        val minutes = TimeUnit.SECONDS.toMinutes(offset) % 60
        val offsetString = String.format(Locale.getDefault(), "%+03d:%02d", hours, minutes) // e.g., "+02:00"
        val timeZoneWithOffset = ZoneId.ofOffset("UTC", java.time.ZoneOffset.of(offsetString))

        // 3. Convert the UTC Instant to the local ZonedDateTime
        val localDateTime = ZonedDateTime.ofInstant(utcInstant, timeZoneWithOffset)

        // 4. Format it into a human-readable string
        val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        return listOf(
            localDateTime.format(dateFormatter),
            localDateTime.format(timeFormatter)
        )
    }
}