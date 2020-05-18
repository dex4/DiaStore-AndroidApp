package com.diastore.feature.entrydetails

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.diastore.EntryDetailsTimeFieldBinding
import com.diastore.R

class EntryDetailsTimeField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyle, defStyleRes) {
    private val binding: EntryDetailsTimeFieldBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_entry_details_time_field,
        this,
        true
    )

    fun setEntryTimeText(entryTime: String?) {
        binding.entryTime.text = entryTime
    }
}