package com.diastore.feature.authentication

import android.util.Patterns

val EMAIL_REGEX = Patterns.EMAIL_ADDRESS.toRegex()
val PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$".toRegex()