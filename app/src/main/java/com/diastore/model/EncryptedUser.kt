package com.diastore.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "encryptedUser")
data class EncryptedUser(
    @PrimaryKey(autoGenerate = false)
    val id: UUID = UUID.randomUUID(),
    val email: String,
    val weight: String,
    val height: String,
    val carbsToInsulinRatio: String,
    val bloodGlucoseToInsulinRatio: String,
    val age: String
)