package com.diastore.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.util.UUID

@JsonClass(generateAdapter = true)
@Entity(tableName = "users")
@Parcelize
data class User(
    @Json(name = "userId")
    @PrimaryKey(autoGenerate = false)
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val weight: Int,
    val height: Int,
    val carbsToInsulinRatio: Int,
    val bloodGlucoseToInsulinRatio: Int,
    val age: Int
) : Parcelable