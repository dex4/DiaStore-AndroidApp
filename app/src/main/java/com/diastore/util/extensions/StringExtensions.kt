package com.diastore.util.extensions

import com.diastore.feature.authentication.EMAIL_REGEX
import com.diastore.feature.authentication.PASSWORD_REGEX

fun String.isValidEmail() = matches(EMAIL_REGEX)
fun String.isValidPassword() = matches(PASSWORD_REGEX)