package com.diastore.util

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

object EncryptionUtils {
    private const val KEY_ALIAS = "KEY_ALIAS"
    private const val KEY_SIZE_IN_BITS = 256
    private const val ENCRYPTION_ALGORITHM = "AES/CBC/PKCS7Padding"
    private const val IV_SEPARATOR = "]"

    private var key: SecretKey? = null
    private val cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM)

    init {
        initKey()
    }

    fun encrypt(plainText: String): String {
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val ivString = Base64.encodeToString(cipher.iv, Base64.DEFAULT)
        val bytes = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))

        return ivString + IV_SEPARATOR + Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    fun encrypt(plainText: Int): String {
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val ivString = Base64.encodeToString(cipher.iv, Base64.DEFAULT)
        val bytes = cipher.doFinal(plainText.toString().toByteArray(Charsets.UTF_8))

        return ivString + IV_SEPARATOR + Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    fun decrypt(encryptedText: String): String {
        var iv: String
        var textToDecrypt: String
        with(encryptedText.split(IV_SEPARATOR.toRegex())) {
            iv = this[0]
            textToDecrypt = this[1]
        }
        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(Base64.decode(iv, Base64.DEFAULT)))

        return String(
            cipher.doFinal(
                Base64.decode(textToDecrypt, Base64.DEFAULT)
            )
        ).trim()
    }

    private fun initKey() {
        val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
        val mKey = keyStore.getKey(KEY_ALIAS, null)
        key = if (mKey != null) {
            (mKey as SecretKey)
        } else {
            generateKey()
        }
    }

    private fun generateKey(): SecretKey =
        KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            "AndroidKeyStore"
        ).apply {
            init(
                KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setKeySize(KEY_SIZE_IN_BITS)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .setUserAuthenticationValidityDurationSeconds(10)
                    .setUserAuthenticationRequired(true)
                    .build()
            )
        }.generateKey()

}