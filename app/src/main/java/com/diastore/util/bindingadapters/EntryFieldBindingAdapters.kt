package com.diastore.util.bindingadapters

import androidx.databinding.BindingAdapter
import com.diastore.feature.home.EntryField

@BindingAdapter("entryValue")
fun EntryField.setValue(entryValue: Number) {
    setEntryValue(entryValue.toString())
}