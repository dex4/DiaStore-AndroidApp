package com.diastore.model.dto

import com.squareup.moshi.JsonClass
import java.util.UUID

@JsonClass(generateAdapter = true)
data class UpdateUserInfoDTO(
    val userId: UUID,
    val weight: Int,
    val height: Int,
    val carbsToInsulinRatio: Int,
    val bloodGlucoseToInsulinRatio: Int,
    val age: Int
)