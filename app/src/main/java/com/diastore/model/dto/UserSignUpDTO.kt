package com.diastore.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserSignUpDTO(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val weight: Int,
    val height: Int,
    val carbsToInsulinRatio: Int,
    val bloodGlucoseToInsulinRatio: Int,
    val age: Int
)