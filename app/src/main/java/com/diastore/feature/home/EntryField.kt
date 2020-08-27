package com.diastore.feature.home

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.diastore.R
import com.diastore.databinding.ViewEntryFieldBinding

class EntryField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyle, defStyleRes) {
    private val binding: ViewEntryFieldBinding = ViewEntryFieldBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    init {
        val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.EntryField, defStyle, 0)
        binding.entryName.text = attributeSet.getString(R.styleable.EntryField_entryName)
        binding.entryValue.text = attributeSet.getString(R.styleable.EntryField_entryValue)
        attributeSet.recycle()
    }

    fun setEntryValue(value: String) {
        binding.entryValue.text = value
    }
}