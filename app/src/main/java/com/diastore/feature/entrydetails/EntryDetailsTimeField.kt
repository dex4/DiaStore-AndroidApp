package com.diastore.feature.entrydetails

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.diastore.databinding.ViewEntryDetailsTimeFieldBinding

class EntryDetailsTimeField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyle, defStyleRes) {
    private val binding = ViewEntryDetailsTimeFieldBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    fun setEntryTimeText(entryTime: String?) {
        binding.entryTime.text = entryTime
    }
}