package com.diastore.util.bindingadapters

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("isInputValid", "errorMessage")
fun TextInputLayout.setErrorMessage(isInputValid: Boolean, errorMessage: String) {
    error = if (!isInputValid) {
        errorMessage
    } else {
        null
    }
}