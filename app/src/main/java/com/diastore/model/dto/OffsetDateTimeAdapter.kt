package com.diastore.model.dto

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.OffsetDateTime

class OffsetDateTimeAdapter {
    @ToJson
    fun toJson(dateTime: OffsetDateTime): String = dateTime.toString()

    @FromJson
    fun fromJson(dateTime: String): OffsetDateTime = OffsetDateTime.parse(dateTime)
}