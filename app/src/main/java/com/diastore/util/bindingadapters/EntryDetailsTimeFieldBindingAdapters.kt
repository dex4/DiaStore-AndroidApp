package com.diastore.util.bindingadapters

import androidx.databinding.BindingAdapter
import com.diastore.feature.entrydetails.EntryDetailsTimeField
import org.threeten.bp.OffsetDateTime
import java.util.Locale

@BindingAdapter("entryTime", requireAll = false)
fun EntryDetailsTimeField.setEntryTime(entryTime: OffsetDateTime) {
    setEntryTimeText(entryTime.format(org.threeten.bp.format.DateTimeFormatter.ofPattern("dd/MM/yy hh:mm", Locale.ENGLISH)))
}