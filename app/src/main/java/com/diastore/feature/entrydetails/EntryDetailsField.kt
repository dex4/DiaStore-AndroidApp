package com.diastore.feature.entrydetails

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.InverseBindingListener
import androidx.databinding.InverseBindingMethod
import androidx.databinding.InverseBindingMethods
import com.diastore.EntryDetailsFieldBinding
import com.diastore.R
import com.diastore.util.AbstractTextWatcher

@InverseBindingMethods(
    InverseBindingMethod(type = EntryDetailsField::class, attribute = "fieldValue", event = "fieldValueAttrChanged")
)

class EntryDetailsField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyle, defStyleRes) {
    private val binding: EntryDetailsFieldBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_entry_details_field,
        this,
        true
    )
    private var fieldValue: String? = ""
    private var inverseBindingValueListener: InverseBindingListener? = null

    init {
        val attributeSet = context.obtainStyledAttributes(attrs, R.styleable.EntryDetailsField, defStyle, 0)
        binding.fieldName.text = attributeSet.getString(R.styleable.EntryDetailsField_fieldName)
        binding.fieldName.setCompoundDrawablesWithIntrinsicBounds(
            attributeSet.getDrawable(R.styleable.EntryDetailsField_fieldDrawable),
            null,
            null,
            null
        )
        binding.fieldValue.hint = attributeSet.getString(R.styleable.EntryDetailsField_fieldValueHint)
        attributeSet.recycle()

        binding.fieldValue.addTextChangedListener(object : AbstractTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                fieldValue = s.toString()
                inverseBindingValueListener?.onChange()
            }
        })
    }

    fun getFieldValue(): String? = fieldValue

    fun setFieldValue(value: String?) {
        if (fieldValue != value) {
            fieldValue = value
            binding.fieldValue.setText(fieldValue ?: "")
        }
    }

    fun setFieldValueAttrChanged(inverseBindingListener: InverseBindingListener?) {
        inverseBindingListener?.let {
            inverseBindingValueListener = inverseBindingListener
        }
    }
}