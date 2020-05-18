package com.diastore.feature.home

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.diastore.EntryFieldBinding
import com.diastore.R

class EntryField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyle, defStyleRes) {
    private val binding: EntryFieldBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_entry_field,
        this,
        true
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