package com.diastore.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "users")
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
)