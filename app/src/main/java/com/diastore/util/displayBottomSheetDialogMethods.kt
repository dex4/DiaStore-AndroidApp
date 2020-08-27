package com.diastore.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.diastore.databinding.BottomSheetProfileNumberPickerBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

fun displayIntPickerBottomSheetDialog(
    context: Context,
    layoutInflater: LayoutInflater,
    parent: ViewGroup,
    minValue: Int,
    maxValue: Int,
    selectedValue: Int?,
    dialogTitle: String,
    stepSize: Int = 1,
    onConfirmClickListener: ((Int) -> Unit)? = null,
    onCancelClickListener: (() -> Unit)? = null
) {
    val dialog = BottomSheetDialog(context)
    val binding = BottomSheetProfileNumberPickerBinding.inflate(
        layoutInflater,
        parent,
        false
    )
    binding.sheetTitle.text = dialogTitle
    binding.agePicker.minValue = minValue
    binding.agePicker.maxValue = maxValue
    binding.agePicker.value = selectedValue ?: minValue
    binding.agePicker.wrapSelectorWheel = false
    binding.buttonConfirm.setOnClickListener {
        onConfirmClickListener?.invoke(binding.agePicker.value * stepSize)
        dialog.dismiss()
    }
    binding.buttonCancel.setOnClickListener {
        onCancelClickListener?.invoke()
        dialog.dismiss()
    }

    if (stepSize != 1) {
        binding.agePicker.setFormatter {
            (it * stepSize).toString()
        }
    }

    dialog.setContentView(binding.root)
    dialog.show()
}