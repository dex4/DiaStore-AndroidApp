package com.diastore.shared

import android.content.Context
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.Fragment
import com.diastore.R
import java.util.concurrent.Executor

typealias OnAuthenticationSuccessCallback = (BiometricPrompt.AuthenticationResult) -> Unit

class DiaStoreBiometricPrompt(
    context: Context,
    fragment: Fragment,
    executor: Executor,
    onAuthenticationSuccessCallback: OnAuthenticationSuccessCallback
) {
    private val biometricPrompt: BiometricPrompt
    private val promptInfo: BiometricPrompt.PromptInfo

    init {
        biometricPrompt = BiometricPrompt(fragment, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(
                    context,
                    errString,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                onAuthenticationSuccessCallback.invoke(result)
            }

            override fun onAuthenticationFailed() {
                Toast.makeText(
                    context,
                    context.getString(R.string.biometric_prompt_authentication_authentication_failed_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(context.getString(R.string.biometric_prompt_authentication_title))
            .setSubtitle(context.getString(R.string.biometric_prompt_authentication_subtitle))
            .setNegativeButtonText(context.getString(R.string.biometric_prompt_authentication_negative_button_text))
            .build()
    }

    fun authenticate() {
        biometricPrompt.authenticate(promptInfo)
    }


}