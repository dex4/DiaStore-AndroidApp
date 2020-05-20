package com.diastore.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "id") val id: String,
    @Json(name = "firstName") val firstName: String,
    @Json(name = "lastName") val lastName: String,
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String,
    @Json(name = "weight") val weight: Int,
    @Json(name = "height") val height: Int,
    @Json(name = "carbsToInsulinRatio") val carbsToInsulinRatio: Double,
    @Json(name = "bloodGlucoseToInsulinRatio") val bloodGlucoseToInsulinRatio: Double,
    @Json(name = "birthDate") val birthDate: String,
    @Transient
    val age: Int = 24
) : Parcelable
