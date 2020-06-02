package com.diastore.util.extensions

import com.diastore.model.EncryptedUser
import com.diastore.model.User
import com.diastore.util.EncryptionUtils

fun User.encryptUser(): EncryptedUser {
    val encryptedWeight = EncryptionUtils.encrypt(weight)
    val encryptedHeight = EncryptionUtils.encrypt(height)
    val encryptedCTI = EncryptionUtils.encrypt(carbsToInsulinRatio)
    val encryptedBTI = EncryptionUtils.encrypt(bloodGlucoseToInsulinRatio)
    val encryptedAge = EncryptionUtils.encrypt(age)
    val encryptedEmail = EncryptionUtils.encrypt(email)

    return EncryptedUser(
        id,
        encryptedEmail,
        encryptedWeight,
        encryptedHeight,
        encryptedCTI,
        encryptedBTI,
        encryptedAge
    )
}