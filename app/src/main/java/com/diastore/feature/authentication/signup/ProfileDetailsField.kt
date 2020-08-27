package com.diastore.feature.authentication.signup

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.diastore.R
import com.diastore.databinding.ViewProfileFieldBinding

class ProfileDetailsField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyle, defStyleRes) {
    private val binding = ViewProfileFieldBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    init {
        val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.ProfileDetailsField, defStyle, 0)
        binding.fieldName.text = attributeSet.getString(R.styleable.ProfileDetailsField_profileFieldName)
        attributeSet.recycle()
    }

    fun setFieldName(fieldName: String?) {
        binding.fieldName.text = fieldName
    }

    fun setFieldValue(fieldFormattedValue: String) {
        binding.fieldValue.text = fieldFormattedValue
    }
}