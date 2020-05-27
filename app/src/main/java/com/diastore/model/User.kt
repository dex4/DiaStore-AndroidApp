package com.diastore.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.UUID

@Entity(tableName = "users")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: UUID = UUID.randomUUID(),
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